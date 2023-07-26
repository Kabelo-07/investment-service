package com.investment.domain.repository;

import com.investment.AbstractRepositoryTest;
import com.investment.domain.entity.Product;
import com.investment.domain.valueobjects.ProductType;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductRepositoryTest extends AbstractRepositoryTest<ProductRepository> {


    @Test
    void willSaveNewProduct() {
        //given
        Product product = Product.builder()
                .productName("Classic Investment")
                .productType(ProductType.SAVINGS)
                .build();

        //when
        product = entityManager.persistAndFlush(product);

        //then
        Optional<Product> optionalProduct = repository.findById(product.getId());
        assertThat(optionalProduct).isPresent();
        assertThat(optionalProduct.get().getProductName()).isEqualTo(product.getProductName());
        assertThat(optionalProduct.get().getProductType()).isEqualTo(product.getProductType());
    }

    @Test
    void willThrowConstraintViolation_whenProductName_isNotSet() {
        //given
        Product product = Product.builder()
                .productType(ProductType.SAVINGS)
                .build();

        //then
        assertThatThrownBy(() -> entityManager.persistAndFlush(product))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("productName must not be blank");
    }

    @Test
    void willThrowConstraintViolation_whenProductType_isNotSet() {
        //given
        Product product = Product.builder()
                .productName("A product")
                .build();

        //then
        assertThatThrownBy(() -> entityManager.persistAndFlush(product))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("productType must be provided");
    }

}