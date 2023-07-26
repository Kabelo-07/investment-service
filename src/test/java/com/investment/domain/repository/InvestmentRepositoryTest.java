package com.investment.domain.repository;

import com.investment.AbstractRepositoryTest;
import com.investment.domain.entity.Investment;
import com.investment.domain.entity.Product;
import com.investment.domain.valueobjects.ProductType;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InvestmentRepositoryTest extends AbstractRepositoryTest<InvestmentRepository> {


    @Test
    void willCreateAndReturnInvestmentWhenExists() {
        //given
        Product product = Product.builder()
                .productType(ProductType.SAVINGS)
                .productName("Classic")
                .build();

        //and
        product = entityManager.persistAndFlush(product);

        Investment investment = Investment.builder()
                .investorId(UUID.randomUUID())
                .product(product)
                .balance(BigDecimal.valueOf(2500.89))
                .build();

        //when
        investment = entityManager.persistAndFlush(investment);

        //then
        List<Investment> products = repository.findByInvestorId(investment.getInvestorId());
        assertThat(products).hasSize(1);
        assertThat(products.get(0).getCreatedDate()).isNotNull();
        assertThat(products.get(0).getUpdatedDate()).isNotNull();
    }

    @Test
    void willThrowConstraintViolation_whenProductIsNotSet_onInvestment() {
        //given
        Investment investment = Investment.builder()
                .investorId(UUID.randomUUID())
                .balance(BigDecimal.valueOf(2500.89))
                .build();

        //when
        assertThatThrownBy(() -> entityManager.persistAndFlush(investment))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("product must be provided");
    }

    @Test
    void willThrowConstraintViolation_whenInvestorIsNotSet_onInvestment() {
        //given
        Product product = Product.builder()
                .productType(ProductType.SAVINGS)
                .productName("Classic")
                .build();

        //and
        product = entityManager.persistAndFlush(product);

        Investment investment = Investment.builder()
                .product(product)
                .balance(BigDecimal.valueOf(2500.89))
                .build();

        //when
        assertThatThrownBy(() -> entityManager.persistAndFlush(investment))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("investorId must be provided");
    }
}