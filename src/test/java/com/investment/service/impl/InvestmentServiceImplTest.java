package com.investment.service.impl;

import com.investment.AbstractInvestmentTestHelper;
import com.investment.domain.entity.Investment;
import com.investment.domain.entity.Investor;
import com.investment.domain.entity.Product;
import com.investment.domain.valueobjects.ProductType;
import com.investment.domain.repository.InvestorRepository;
import com.investment.model.InvestmentModel;
import com.investment.service.contract.InvestmentService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static com.investment.TestData.buildNewInvestor;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class InvestmentServiceImplTest extends AbstractInvestmentTestHelper {

    @Autowired
    InvestorRepository investorRepository;

    @Autowired
    InvestmentService investmentService;

    @AfterEach
    public void cleanUp() {
        investmentRepository.deleteAll();
        investorRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    void willReturnInvestmentFoInvestor_whenInvestmentsExists() {
        //given
        Investor investor = investorRepository.save(buildNewInvestor("James", "Bond", "0771231234", "jb@mi6-007.uk"));

        //and
        Product product = createProduct(ProductType.SAVINGS);

        //and
        Investment investment = createInvestment(product, BigDecimal.valueOf(5500), investor.getId());

        //when
        List<InvestmentModel> models = investmentService.findInvestmentsByInvestorId(investor.getId());

        //then
        assertThat(models).hasSize(1);
        assertThat(models.get(0).getBalance()).isEqualTo(investment.getBalance().setScale(2, RoundingMode.HALF_EVEN));
        assertThat(models.get(0).getId()).isEqualTo(investment.getId());
        assertThat(models.get(0).getProduct().getProductName()).isEqualTo(investment.getProductName());
        assertThat(models.get(0).getProduct().getProductType().name()).isEqualTo(investment.getProductType().name());
    }

    @Test
    void willNotReturnInvestmentsFoInvestor_whenInvestmentsDoNotExists() {
        //given
        Investor investor = investorRepository.save(buildNewInvestor("James", "Bond", "0771231234", "jb@mi6-007.uk"));

        //when
        List<InvestmentModel> models = investmentService.findInvestmentsByInvestorId(investor.getId());

        //then
        assertThat(models).isEmpty();
    }
    
}