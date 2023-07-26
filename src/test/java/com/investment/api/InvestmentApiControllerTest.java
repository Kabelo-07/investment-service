package com.investment.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.investment.AbstractInvestmentTestHelper;
import com.investment.domain.entity.Investment;
import com.investment.domain.entity.Investor;
import com.investment.domain.entity.Product;
import com.investment.domain.valueobjects.ProductType;
import com.investment.domain.repository.InvestorRepository;
import com.investment.model.BankingDetail;
import com.investment.model.WithdrawalRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static com.investment.TestData.buildNewInvestor;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class InvestmentApiControllerTest extends AbstractInvestmentTestHelper {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    InvestorRepository investorRepository;

    @Autowired
    ObjectMapper objectMapper;

    @AfterEach
    void cleanUp() {
        investorRepository.deleteAll();
        investmentRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    void willProcessSavingsInvestmentProductWithdrawal() throws Exception {
        //given
        Investor investor = buildNewInvestor("Jon", "Snow", "0881234567", "jon@snow.com");
        investorRepository.save(investor);

        //and
        Product product = createProduct(ProductType.SAVINGS);
        //and
        Investment investment = createInvestment(product, BigDecimal.valueOf(25000), investor.getId());

        //when
        WithdrawalRequest request = buildWithdrawalRequest(15000, investor.getId(), investor.getFirstName(), investor.getLastName());

        ResultActions actions = mockMvc.perform(post("/investments/{investment-id}/withdraw", investment.getId())
                        .content(objectMapper.writeValueAsBytes(request))
                        .contentType(MediaType.APPLICATION_JSON));

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.reference").isNotEmpty())
                .andExpect(jsonPath("$.new_balance").value("10000.00"));
    }

    @Test
    @DisplayName("Test withdrawal fails when minimum amount specified is less than ZAR 100")
    void willNotProcessSavingsInvestmentProductWithdrawal_whenWithdrawalAmountIsLessThanMinimumAmount() throws Exception {
        //given
        WithdrawalRequest request = buildWithdrawalRequest(0, UUID.randomUUID(), "Sean", "Jean");

        ResultActions actions = mockMvc.perform(post("/investments/{investment-id}/withdraw", UUID.randomUUID())
                .content(objectMapper.writeValueAsBytes(request))
                .contentType(MediaType.APPLICATION_JSON));

        //then
        actions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid request sent"))
                .andExpect(jsonPath("$.errors[0]").value("amount: minimum withdrawal amount is 100 ZAR"));
    }

    @Test
    void willNotProcessSavingsInvestmentProductWithdrawal_whenBankingDetailsAreNotProvided() throws Exception {
        //given
        Investor investor = buildNewInvestor("Jon", "Snow", "0741234567", "jon@snow.com");
        investorRepository.save(investor);

        //and
        Product product = createProduct(ProductType.SAVINGS);
        //and
        Investment investment = createInvestment(product, BigDecimal.valueOf(25000), investor.getId());

        //when
        WithdrawalRequest request = WithdrawalRequest.builder()
                .amount(BigDecimal.valueOf(150))
                .build();

        ResultActions actions = mockMvc.perform(post("/investments/{investment-id}/withdraw", investment.getId())
                .content(objectMapper.writeValueAsBytes(request))
                .contentType(MediaType.APPLICATION_JSON));

        //then
        actions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid request sent"))
                .andExpect(jsonPath("$.errors[0]").value("bankingDetails: banking details must be provided"));
    }

    @Test
    void willNotProcessSavingsInvestmentProductWithdrawal_whenBankingNameIsMissing() throws Exception {
        //given
        WithdrawalRequest request = WithdrawalRequest.builder()
                .amount(BigDecimal.valueOf(150))
                .build();

        ResultActions actions = mockMvc.perform(post("/investments/{investment-id}/withdraw", UUID.randomUUID())
                .content(objectMapper.writeValueAsBytes(request))
                .contentType(MediaType.APPLICATION_JSON));

        //then
        actions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid request sent"))
                .andExpect(jsonPath("$.errors[0]").value("bankingDetails: banking details must be provided"));
    }

    @Test
    void willNotProcessSavingsInvestmentProductWithdrawal_whenInvalidInvestmentProductId_isProvided() throws Exception {
        //given
        WithdrawalRequest request = buildWithdrawalRequest(500, UUID.randomUUID(), "Jay", "Zeeh");

        ResultActions actions = mockMvc.perform(post("/investments/{investment-id}/withdraw", UUID.randomUUID())
                .content(objectMapper.writeValueAsBytes(request))
                .contentType(MediaType.APPLICATION_JSON));

        //then
        actions.andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.error_key").value("invalid.withdrawal.request"))
                .andExpect(jsonPath("$.message", containsString("Invalid withdrawal request for investment with given id")));
    }

    @Test
    @DisplayName("Wont process Retirement investment product withdrawal for investor under age of 65")
    void willNotProcessRetirementInvestmentProductWithdrawal_whenInvestorAgeDoesNotMeetMinimumAge() throws Exception {
        //given
        Investor investor = buildNewInvestor("Jon", "Snow", "0811234567",
                "jon@snow.com",
                LocalDate.now().minusYears(23));
        investorRepository.save(investor);

        //and
        Product product = createProduct(ProductType.RETIREMENT);
        //and
        Investment investment = createInvestment(product, BigDecimal.valueOf(459000), investor.getId());

        //when
        WithdrawalRequest request = buildWithdrawalRequest(6000, investor.getId(), investor.getFirstName(), investor.getLastName());

        ResultActions actions = mockMvc.perform(post("/investments/{investment-id}/withdraw", investment.getId())
                .content(objectMapper.writeValueAsBytes(request))
                .contentType(MediaType.APPLICATION_JSON));

        //then
        actions.andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.error_key").value("invalid.withdrawal.age"))
                .andExpect(jsonPath("$.message").value("Investor must be 65 years or older to make a Retirement withdrawal"));
    }

    @Test
    @DisplayName("Will process Retirement investment product withdrawal for investor who are 65years or older")
    void willProcessRetirementInvestmentProductWithdrawal_whenInvestorMeetsMinimumAge() throws Exception {
        //given
        Investor investor = buildNewInvestor("Jon", "Snow", "0821734567",
                "jon@snow.com",
                LocalDate.now().minusYears(67));
        investor = investorRepository.save(investor);

        //and
        Product product = createProduct(ProductType.RETIREMENT);
        //and
        Investment investment = createInvestment(product, BigDecimal.valueOf(10000), investor.getId());

        //when
        WithdrawalRequest request = buildWithdrawalRequest(6000, investor.getId(), investor.getFirstName(), investor.getLastName());

        ResultActions actions = mockMvc.perform(post("/investments/{investment-id}/withdraw", investment.getId())
                .content(objectMapper.writeValueAsBytes(request))
                .contentType(MediaType.APPLICATION_JSON));

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.reference").isNotEmpty())
                .andExpect(jsonPath("$.new_balance").value("4000.00"));
    }

    @Test
    @DisplayName("Will not process investment withdrawal when withdrawal amount is more than 90% of current balance")
    void willNotProcessInvestmentProductWithdrawal_whenAmountRequest_isMoreThanNinetyPercentOfCurrentBalance() throws Exception {
        //given
        Investor investor = buildNewInvestor("Jon", "Snow", "0891774567",
                "jon@snow.com",
                LocalDate.now().minusYears(67));
        investor = investorRepository.save(investor);

        //and
        Product product = createProduct(ProductType.RETIREMENT);
        //and
        Investment investment = createInvestment(product, BigDecimal.valueOf(10000), investor.getId());

        //when
        WithdrawalRequest request = buildWithdrawalRequest(9100, investor.getId(), investor.getFirstName(), investor.getLastName());

        ResultActions actions = mockMvc.perform(post("/investments/{investment-id}/withdraw", investment.getId())
                .content(objectMapper.writeValueAsBytes(request))
                .contentType(MediaType.APPLICATION_JSON));

        //then
        actions.andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.error_key").value("invalid.withdrawal.amount"))
                .andExpect(jsonPath("$.message").value("Cannot withdraw more than 90 percent of investment"));
    }

    private WithdrawalRequest buildWithdrawalRequest(long val, UUID investor, String firstName, String lastName) {
        return WithdrawalRequest.builder()
                .amount(BigDecimal.valueOf(val))
                .bankingDetails(BankingDetail.builder()
                        .accountHolderName(String.format("%s %s", firstName, lastName))
                        .accountNumber("6789091245")
                        .bankName("Second International Bank")
                        .branchCode("149544")
                        .build())
                .build();
    }

}