package com.investment.api;

import com.investment.AbstractInvestmentTestHelper;
import com.investment.domain.entity.Investment;
import com.investment.domain.entity.Investor;
import com.investment.domain.entity.Product;
import com.investment.domain.valueobjects.ProductType;
import com.investment.domain.repository.InvestorRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;

import static com.investment.TestData.buildNewInvestor;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class InvestorsApiControllerTest extends AbstractInvestmentTestHelper {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    InvestorRepository investorRepository;
    
    @AfterEach
    void cleanUp() {
        investmentRepository.deleteAll();
        productRepository.deleteAll();
        investorRepository.deleteAll();
    }

    @Test
    void willReturnAllInvestors_whenInvestorsExist() throws Exception {
        //given
        Investor investor = buildNewInvestor("Jon", "Snow", "0891234000", "jon@snow.com");
        Investor investor1 = buildNewInvestor("Lewis", "Hamilton", "0829001456", "lh@f1.com");
        Investor investor2 = buildNewInvestor("Mark", "Fish", "0671239088", "mfish@safa.com");
        investorRepository.saveAll(Arrays.asList(investor, investor1, investor2));

        //when
        ResultActions actions = mockMvc.perform(get("/investors")
                        .contentType(MediaType.APPLICATION_JSON));

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(3))
                .andExpect(jsonPath("$.content.*", hasSize(3)))
                .andExpect(jsonPath("$.content[0].id").value(String.valueOf(investor.getId())))
                .andExpect(jsonPath("$.content[0].first_name").value(investor.getFirstName()))
                .andExpect(jsonPath("$.content[0].last_name").value(investor.getLastName()))
                .andExpect(jsonPath("$.content[0].date_of_birth").value(investor.getDateOfBirth().toString()))
                .andExpect(jsonPath("$.content[0].contacts.email_address").value(investor.getEmailAddress()))
                .andExpect(jsonPath("$.content[0].contacts.mobile_number").value(investor.getMobileNumber()))
                .andExpect(jsonPath("$.content[0].address.address_line1").value(investor.getAddress().getAddressLine1()))
                .andExpect(jsonPath("$.content[0].address.address_line2").doesNotExist())
                .andExpect(jsonPath("$.content[0].address.suburb").value(investor.getAddress().getSuburb()))
                .andExpect(jsonPath("$.content[0].address.city").value(investor.getAddress().getCity()))
                .andExpect(jsonPath("$.content[0].address.province").value(investor.getAddress().getProvince()))
                .andExpect(jsonPath("$.content[0].address.postal_code").value(investor.getAddress().getPostalCode()));

        //and
        actions.andExpect(jsonPath("$.content[1].id").value(String.valueOf(investor1.getId())))
                .andExpect(jsonPath("$.content[1].first_name").value(investor1.getFirstName()))
                .andExpect(jsonPath("$.content[1].last_name").value(investor1.getLastName()))
                .andExpect(jsonPath("$.content[1].date_of_birth").value(investor1.getDateOfBirth().toString()))
                .andExpect(jsonPath("$.content[1].contacts.email_address").value(investor1.getEmailAddress()))
                .andExpect(jsonPath("$.content[1].contacts.mobile_number").value(investor1.getMobileNumber()))
                .andExpect(jsonPath("$.content[1].address.address_line1").value(investor1.getAddress().getAddressLine1()))
                .andExpect(jsonPath("$.content[1].address.address_line2").doesNotExist())
                .andExpect(jsonPath("$.content[1].address.suburb").value(investor1.getAddress().getSuburb()))
                .andExpect(jsonPath("$.content[1].address.city").value(investor1.getAddress().getCity()))
                .andExpect(jsonPath("$.content[1].address.province").value(investor1.getAddress().getProvince()))
                .andExpect(jsonPath("$.content[1].address.postal_code").value(investor1.getAddress().getPostalCode()));

        //and
        actions.andExpect(jsonPath("$.content[2].id").value(String.valueOf(investor2.getId())))
                .andExpect(jsonPath("$.content[2].first_name").value(investor2.getFirstName()))
                .andExpect(jsonPath("$.content[2].last_name").value(investor2.getLastName()))
                .andExpect(jsonPath("$.content[2].date_of_birth").value(investor2.getDateOfBirth().toString()))
                .andExpect(jsonPath("$.content[2].contacts.email_address").value(investor2.getEmailAddress()))
                .andExpect(jsonPath("$.content[2].contacts.mobile_number").value(investor2.getMobileNumber()))
                .andExpect(jsonPath("$.content[2].address.address_line1").value(investor2.getAddress().getAddressLine1()))
                .andExpect(jsonPath("$.content[2].address.address_line2").doesNotExist())
                .andExpect(jsonPath("$.content[2].address.suburb").value(investor2.getAddress().getSuburb()))
                .andExpect(jsonPath("$.content[2].address.city").value(investor2.getAddress().getCity()))
                .andExpect(jsonPath("$.content[2].address.province").value(investor2.getAddress().getProvince()))
                .andExpect(jsonPath("$.content[2].address.postal_code").value(investor2.getAddress().getPostalCode()));
    }

    @Test
    void willReturnInvestors_filteredByEmail_whenInvestorsWithEmailExist() throws Exception {
        //given
        Investor investor = buildNewInvestor("Jon", "Snow", "0876781209", "jon@gmail.com");
        Investor investor1 = buildNewInvestor("Lewis", "Hamilton", "0829001456", "lh@f1.uk");
        Investor investor2 = buildNewInvestor("Mark", "Fish", "0671239088", "mfish@gmail.za");
        investorRepository.saveAll(Arrays.asList(investor, investor1, investor2));

        //when
        ResultActions actions = mockMvc.perform(get("/investors")
                        .queryParam("email-address", "gmail")
                .contentType(MediaType.APPLICATION_JSON));

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.content.*", hasSize(2)))
                .andExpect(jsonPath("$.content[0].id").value(String.valueOf(investor.getId())))
                .andExpect(jsonPath("$.content[0].first_name").value(investor.getFirstName()))
                .andExpect(jsonPath("$.content[0].last_name").value(investor.getLastName()))
                .andExpect(jsonPath("$.content[0].date_of_birth").value(investor.getDateOfBirth().toString()))
                .andExpect(jsonPath("$.content[0].contacts.email_address").value(investor.getEmailAddress()))
                .andExpect(jsonPath("$.content[0].contacts.mobile_number").value(investor.getMobileNumber()))
                .andExpect(jsonPath("$.content[0].address.address_line1").value(investor.getAddress().getAddressLine1()))
                .andExpect(jsonPath("$.content[0].address.address_line2").doesNotExist())
                .andExpect(jsonPath("$.content[0].address.suburb").value(investor.getAddress().getSuburb()))
                .andExpect(jsonPath("$.content[0].address.city").value(investor.getAddress().getCity()))
                .andExpect(jsonPath("$.content[0].address.province").value(investor.getAddress().getProvince()))
                .andExpect(jsonPath("$.content[0].address.postal_code").value(investor.getAddress().getPostalCode()));

        //and
        actions.andExpect(jsonPath("$.content[1].id").value(String.valueOf(investor2.getId())))
                .andExpect(jsonPath("$.content[1].first_name").value(investor2.getFirstName()))
                .andExpect(jsonPath("$.content[1].last_name").value(investor2.getLastName()))
                .andExpect(jsonPath("$.content[1].date_of_birth").value(investor2.getDateOfBirth().toString()))
                .andExpect(jsonPath("$.content[1].contacts.email_address").value(investor2.getEmailAddress()))
                .andExpect(jsonPath("$.content[1].contacts.mobile_number").value(investor2.getMobileNumber()))
                .andExpect(jsonPath("$.content[1].address.address_line1").value(investor2.getAddress().getAddressLine1()))
                .andExpect(jsonPath("$.content[1].address.address_line2").doesNotExist())
                .andExpect(jsonPath("$.content[1].address.suburb").value(investor2.getAddress().getSuburb()))
                .andExpect(jsonPath("$.content[1].address.city").value(investor2.getAddress().getCity()))
                .andExpect(jsonPath("$.content[1].address.province").value(investor2.getAddress().getProvince()))
                .andExpect(jsonPath("$.content[1].address.postal_code").value(investor2.getAddress().getPostalCode()));

    }

    @Test
    void willReturnInvestorWithMatchingMobileNumber_whenInvestorWithMobileNumberExist() throws Exception {
        //given
        Investor investor = buildNewInvestor("Jon", "Snow", "0780981249", "jon@gmail.com");
        Investor investor1 = buildNewInvestor("Lewis", "Hamilton", "0829001456", "lh@f1.uk");
        Investor investor2 = buildNewInvestor("Mark", "Fish", "0671239088", "mfish@gmail.za");
        investorRepository.saveAll(Arrays.asList(investor, investor1, investor2));

        //when
        ResultActions actions = mockMvc.perform(get("/investors")
                .queryParam("mobile-number", "0829001456")
                .contentType(MediaType.APPLICATION_JSON));

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.content.*", hasSize(1)))
                .andExpect(jsonPath("$.content[0].id").value(String.valueOf(investor1.getId())))
                .andExpect(jsonPath("$.content[0].first_name").value(investor1.getFirstName()))
                .andExpect(jsonPath("$.content[0].last_name").value(investor1.getLastName()))
                .andExpect(jsonPath("$.content[0].date_of_birth").value(investor1.getDateOfBirth().toString()))
                .andExpect(jsonPath("$.content[0].contacts.email_address").value(investor1.getEmailAddress()))
                .andExpect(jsonPath("$.content[0].contacts.mobile_number").value(investor1.getMobileNumber()))
                .andExpect(jsonPath("$.content[0].address.address_line1").value(investor1.getAddress().getAddressLine1()))
                .andExpect(jsonPath("$.content[0].address.address_line2").doesNotExist())
                .andExpect(jsonPath("$.content[0].address.suburb").value(investor1.getAddress().getSuburb()))
                .andExpect(jsonPath("$.content[0].address.city").value(investor1.getAddress().getCity()))
                .andExpect(jsonPath("$.content[0].address.province").value(investor1.getAddress().getProvince()))
                .andExpect(jsonPath("$.content[0].address.postal_code").value(investor1.getAddress().getPostalCode()));

    }

    @Test
    void willReturnInvestorWithFirstName_whenInvestorWithGivenFirstNameExist() throws Exception {
        //given
        Investor investor = buildNewInvestor("Jon", "Snow", "0899091589", "jon@gmail.com");
        Investor investor1 = buildNewInvestor("Lewis", "Hamilton", "0829001456", "lh@f1.uk");
        Investor investor2 = buildNewInvestor("Mark", "Fish", "0671239088", "mfish@gmail.za");
        investorRepository.saveAll(Arrays.asList(investor, investor1, investor2));

        //when
        ResultActions actions = mockMvc.perform(get("/investors")
                .queryParam("first-name", "Mark")
                .contentType(MediaType.APPLICATION_JSON));

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.content.*", hasSize(1)))
                .andExpect(jsonPath("$.content[0].id").value(String.valueOf(investor2.getId())))
                .andExpect(jsonPath("$.content[0].first_name").value(investor2.getFirstName()))
                .andExpect(jsonPath("$.content[0].last_name").value(investor2.getLastName()))
                .andExpect(jsonPath("$.content[0].date_of_birth").value(investor2.getDateOfBirth().toString()))
                .andExpect(jsonPath("$.content[0].contacts.email_address").value(investor2.getEmailAddress()))
                .andExpect(jsonPath("$.content[0].contacts.mobile_number").value(investor2.getMobileNumber()))
                .andExpect(jsonPath("$.content[0].address.address_line1").value(investor2.getAddress().getAddressLine1()))
                .andExpect(jsonPath("$.content[0].address.address_line2").doesNotExist())
                .andExpect(jsonPath("$.content[0].address.suburb").value(investor2.getAddress().getSuburb()))
                .andExpect(jsonPath("$.content[0].address.city").value(investor2.getAddress().getCity()))
                .andExpect(jsonPath("$.content[0].address.province").value(investor2.getAddress().getProvince()))
                .andExpect(jsonPath("$.content[0].address.postal_code").value(investor2.getAddress().getPostalCode()));
    }

    @Test
    void willReturnInvestorWithFirstName_whenInvestorWithGivenLastNameExist() throws Exception {
        //given
        Investor investor = buildNewInvestor("Jon", "Snow", "0600609001", "jon@gmail.com");
        Investor investor1 = buildNewInvestor("Lewis", "Hamilton", "0829001456", "lh@f1.uk");
        Investor investor2 = buildNewInvestor("Mark", "Fish", "0671239088", "mfish@gmail.za");
        investorRepository.saveAll(Arrays.asList(investor, investor1, investor2));

        //when
        ResultActions actions = mockMvc.perform(get("/investors")
                .queryParam("last-name", "Snow")
                .contentType(MediaType.APPLICATION_JSON));

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.content.*", hasSize(1)))
                .andExpect(jsonPath("$.content[0].id").value(String.valueOf(investor.getId())))
                .andExpect(jsonPath("$.content[0].first_name").value(investor.getFirstName()))
                .andExpect(jsonPath("$.content[0].last_name").value(investor.getLastName()))
                .andExpect(jsonPath("$.content[0].date_of_birth").value(investor.getDateOfBirth().toString()))
                .andExpect(jsonPath("$.content[0].contacts.email_address").value(investor.getEmailAddress()))
                .andExpect(jsonPath("$.content[0].contacts.mobile_number").value(investor.getMobileNumber()))
                .andExpect(jsonPath("$.content[0].address.address_line1").value(investor.getAddress().getAddressLine1()))
                .andExpect(jsonPath("$.content[0].address.address_line2").doesNotExist())
                .andExpect(jsonPath("$.content[0].address.suburb").value(investor.getAddress().getSuburb()))
                .andExpect(jsonPath("$.content[0].address.city").value(investor.getAddress().getCity()))
                .andExpect(jsonPath("$.content[0].address.province").value(investor.getAddress().getProvince()))
                .andExpect(jsonPath("$.content[0].address.postal_code").value(investor.getAddress().getPostalCode()));
    }

    @Test
    void willReturnEmptyInvestorsContent_whenInvestorWithGivenLastNameDoesNotExist() throws Exception {
        //given
        Investor investor = buildNewInvestor("Jon", "Snow", "0892234567", "jon@gmail.com");
        Investor investor1 = buildNewInvestor("Lewis", "Hamilton", "0849001456", "lh@f1.uk");
        Investor investor2 = buildNewInvestor("Mark", "Fish", "0671639088", "mfish@gmail.za");
        investorRepository.saveAll(Arrays.asList(investor, investor1, investor2));

        //when
        ResultActions actions = mockMvc.perform(get("/investors")
                .queryParam("last-name", "Mashishi")
                .contentType(MediaType.APPLICATION_JSON));

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(0))
                .andExpect(jsonPath("$.content.*").doesNotExist());
    }

    @Test
    void willReturnInvestor_whenExistsWithGivenId() throws Exception {
        //given
        Investor investor = buildNewInvestor("Jon", "Snow", "0591234567", "jon@snow.com");
        investorRepository.save(investor);

        //when
        ResultActions actions = mockMvc.perform(
                get("/investors/{investor-id}", investor.getId())
                        .contentType(MediaType.APPLICATION_JSON));

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(String.valueOf(investor.getId())))
                .andExpect(jsonPath("$.first_name").value(investor.getFirstName()))
                .andExpect(jsonPath("$.last_name").value(investor.getLastName()))
                .andExpect(jsonPath("$.date_of_birth").value(investor.getDateOfBirth().toString()))
                .andExpect(jsonPath("$.contacts.email_address").value(investor.getEmailAddress()))
                .andExpect(jsonPath("$.contacts.mobile_number").value(investor.getMobileNumber()))
                .andExpect(jsonPath("$.address.address_line1").value(investor.getAddress().getAddressLine1()))
                .andExpect(jsonPath("$.address.address_line2").doesNotExist())
                .andExpect(jsonPath("$.address.suburb").value(investor.getAddress().getSuburb()))
                .andExpect(jsonPath("$.address.city").value(investor.getAddress().getCity()))
                .andExpect(jsonPath("$.address.province").value(investor.getAddress().getProvince()))
                .andExpect(jsonPath("$.address.postal_code").value(investor.getAddress().getPostalCode()));

    }

    @Test
    void willReturnHttp404_whenInvestorDoesNotExists_withGivenId() throws Exception {
        //given - random id
        UUID investorId = UUID.randomUUID();

        //when
        ResultActions actions = mockMvc.perform(
                get("/investors/{investor-id}", investorId)
                        .contentType(MediaType.APPLICATION_JSON));

        //then
        actions.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Investor not found with given id: "+investorId));
    }

    @Test
    void willReturnInvestmentProducts_whenInvestmentExistsForInvestor() throws Exception {
        //given
        Investor investor = buildNewInvestor("Jon", "Snow", "0880880880", "jon@snow.com");
        investorRepository.save(investor);

        //and
        Product retirementProduct = createProduct(ProductType.RETIREMENT);
        Product savingsProduct = createProduct(ProductType.SAVINGS);

        //and
        Investment investment = createInvestment(retirementProduct, BigDecimal.valueOf(500.87), investor.getId());
        Investment investment1 = createInvestment(savingsProduct, BigDecimal.valueOf(21789L), investor.getId());

        //when
        ResultActions actions = mockMvc.perform(
                get("/investors/{investor-id}/investments", investor.getId())
                        .contentType(MediaType.APPLICATION_JSON));

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].investment_product_id").value(String.valueOf(investment.getId())))
                .andExpect(jsonPath("$[0].balance").value(investment.getBalance()))
                .andExpect(jsonPath("$[0].product.name").value(investment.getProductName()))
                .andExpect(jsonPath("$[0].product.type").value(investment.getProductType().name()))
                .andExpect(jsonPath("$[1].investment_product_id").value(String.valueOf(investment1.getId())))
                .andExpect(jsonPath("$[1].balance").value(investment1.getBalance()))
                .andExpect(jsonPath("$[1].product.name").value(investment1.getProductName()))
                .andExpect(jsonPath("$[1].product.type").value(investment1.getProductType().name()));
    }

    @Test
    void willReturnNoContent_whenInvestmentProductDoNotExistsForInvestor() throws Exception {
        //given
        Investor investor = buildNewInvestor("Jon", "Snow", "0671234567", "jon@snow.com");
        investorRepository.save(investor);

        //when
        ResultActions actions = mockMvc.perform(
                get("/investors/{investor-id}/investments", investor.getId())
                        .contentType(MediaType.APPLICATION_JSON));

        //then
        actions.andExpect(status().isNoContent());
    }

}