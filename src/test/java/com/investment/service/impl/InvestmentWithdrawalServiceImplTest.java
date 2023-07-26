package com.investment.service.impl;

import com.investment.AbstractInvestmentTestHelper;
import com.investment.domain.entity.Investment;
import com.investment.domain.entity.Investor;
import com.investment.domain.entity.Product;
import com.investment.domain.valueobjects.ProductType;
import com.investment.domain.repository.InvestorRepository;
import com.investment.events.model.WithdrawalProcessedEvent;
import com.investment.exceptions.InvalidWithdrawalRequestException;
import com.investment.model.WithdrawalRequest;
import com.investment.model.WithdrawalResponse;
import com.investment.service.contract.InvestmentWithdrawalService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static com.investment.TestData.buildNewInvestor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class InvestmentWithdrawalServiceImplTest extends AbstractInvestmentTestHelper {

    @Autowired
    InvestorRepository investorRepository;

    @Autowired
    InvestmentWithdrawalService withdrawalService;

    @AfterEach
    public void cleanUp() {
        investmentRepository.deleteAll();
        investorRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    void willProcessWithdrawalSuccessfully_forInvestor() {
        //given
        Investor investor = investorRepository.save(buildNewInvestor("James", "Bond", "0771231234", "jb@mi6-007.uk"));

        //and
        Product product = createProduct(ProductType.SAVINGS);

        Investment investment = createInvestment(product, BigDecimal.valueOf(2500), investor.getId());

        //when
        WithdrawalRequest request = WithdrawalRequest.builder()
                .amount(BigDecimal.valueOf(1200))
                .build();

        WithdrawalResponse withdrawalResponse = withdrawalService.processWithdrawal(request, investment.getId());

        //then
        investment = investmentRepository.findById(investment.getId()).orElse(null);
        assertThat(withdrawalResponse).isNotNull();
        assertThat(investment).isNotNull();
        assertThat(withdrawalResponse.getReference()).isNotNull();
        assertThat(withdrawalResponse.getNewBalance().longValue()).isEqualTo(1300L);
        assertThat(withdrawalResponse.getNewBalance()).isEqualTo(investment.getBalance());

        //and
        verify(eventPublisher, times(1)).publish(any(WithdrawalProcessedEvent.class));
    }

    @Test
    void willNotProcessWithdrawal_whenInvestmentBalance_isLessThanWithdrawalAmount() {
        //given
        Investor investor = investorRepository.save(buildNewInvestor("James", "Bond", "0771231234", "jb@mi6-007.uk"));

        //and
        Product product = createProduct(ProductType.SAVINGS);

        //and
        Investment investment = createInvestment(product, BigDecimal.valueOf(5500), investor.getId());

        //when
        WithdrawalRequest request = WithdrawalRequest.builder()
                .amount(BigDecimal.valueOf(5600))
                .build();

        //then
        UUID investmentId = investment.getId();
        assertThatThrownBy(() -> withdrawalService.processWithdrawal(request, investmentId))
                .isInstanceOf(InvalidWithdrawalRequestException.class)
                .hasMessage("Balance is less than requested amount");

        //and
        verify(eventPublisher, never()).publish(any(WithdrawalProcessedEvent.class));
    }

    @Test
    void willNotProcessWithdrawal_whenWithdrawalAmount_isOverNinetyPercentageOfBalance() {
        //given
        Investor investor = investorRepository.save(buildNewInvestor("James", "Bond", "0771231234", "jb@mi6-007.uk"));

        //and
        Product product = createProduct(ProductType.SAVINGS);

        //and
        Investment investment = createInvestment(product, BigDecimal.valueOf(5500), investor.getId());

        //when
        WithdrawalRequest request = WithdrawalRequest.builder()
                .amount(BigDecimal.valueOf(5290))
                .build();

        //then
        UUID investmentId = investment.getId();
        assertThatThrownBy(() -> withdrawalService.processWithdrawal(request, investmentId))
                .isInstanceOf(InvalidWithdrawalRequestException.class)
                .hasMessage("Cannot withdraw more than 90 percent of investment");

        //and
        verify(eventPublisher, never()).publish(any(WithdrawalProcessedEvent.class));
    }

    @Test
    void willNotProcessWithdrawal_forRetirementInvestment_whenInvestorAgeIs_lessThanRetirementAge() {
        //given
        Investor investor = investorRepository.save(buildNewInvestor("James", "Bond",
                "0771231234", "jb@mi6-007.uk",
                LocalDate.now().minusYears(34)));

        //and
        Product product = createProduct(ProductType.RETIREMENT);

        //and
        Investment investment = createInvestment(product, BigDecimal.valueOf(25000), investor.getId());

        //when
        WithdrawalRequest request = WithdrawalRequest.builder()
                .amount(BigDecimal.valueOf(5000))
                .build();

        //then
        UUID investmentId = investment.getId();
        assertThatThrownBy(() -> withdrawalService.processWithdrawal(request, investmentId))
                .isInstanceOf(InvalidWithdrawalRequestException.class)
                .hasMessage("Investor must be 65 years or older to make a Retirement withdrawal");

        //and
        verify(eventPublisher, never()).publish(any(WithdrawalProcessedEvent.class));
    }

    @Test
    void willProcessWithdrawal_forRetirementInvestment_whenInvestorAgeIs_greaterThanRetirementAge() {
        //given
        Investor investor = investorRepository.save(buildNewInvestor("James", "Bond",
                "0771231234", "jb@mi6-007.uk",
                LocalDate.now().minusYears(68)));

        //and
        Product product = createProduct(ProductType.RETIREMENT);

        //and
        Investment investment = createInvestment(product, BigDecimal.valueOf(25000), investor.getId());

        //when
        WithdrawalRequest request = WithdrawalRequest.builder()
                .amount(BigDecimal.valueOf(5000))
                .build();

        //then
        WithdrawalResponse withdrawalResponse = withdrawalService.processWithdrawal(request, investment.getId());

        //and
        verify(eventPublisher, times(1)).publish(any(WithdrawalProcessedEvent.class));

        //and
        investment = investmentRepository.findById(investment.getId()).orElse(null);
        assertThat(withdrawalResponse).isNotNull();
        assertThat(investment).isNotNull();
        assertThat(withdrawalResponse.getReference()).isNotNull();
        assertThat(withdrawalResponse.getNewBalance().longValue()).isEqualTo(20000L);
        assertThat(withdrawalResponse.getNewBalance()).isEqualTo(investment.getBalance());
    }

    @Test
    void willProcessWithdrawal_forSavingsInvestment_whenInvestorAgeIs_lessThanRetirementAge() {
        //given
        Investor investor = investorRepository.save(buildNewInvestor("Jon", "Snow",
                "0891238901", "js@mgot.io",
                LocalDate.now().minusYears(44)));

        //and
        Product product = createProduct(ProductType.SAVINGS);

        //and
        Investment investment = createInvestment(product, BigDecimal.valueOf(25000), investor.getId());

        //when
        WithdrawalRequest request = WithdrawalRequest.builder()
                .amount(BigDecimal.valueOf(15000))
                .build();
        WithdrawalResponse withdrawalResponse = withdrawalService.processWithdrawal(request, investment.getId());

        //then
        assertThat(withdrawalResponse).isNotNull();
        assertThat(withdrawalResponse.getReference()).isNotNull();
        assertThat(withdrawalResponse.getNewBalance().longValue()).isEqualTo(10000L);

        //and
        verify(eventPublisher, times(1)).publish(any(WithdrawalProcessedEvent.class));
    }
    
}