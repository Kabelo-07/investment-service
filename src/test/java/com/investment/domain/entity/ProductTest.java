package com.investment.domain.entity;

import com.investment.domain.valueobjects.ProductType;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {

    @Test
    void productsWithDifferentIds_willNotBeEquals() {
        //given
        Product product = Product.builder()
                .id(UUID.randomUUID())
                .productType(ProductType.RETIREMENT)
                .productName("Classic")
                .build();

        //and
        Product product2 = Product.builder()
                .id(UUID.randomUUID())
                .productName("Premium")
                .productType(ProductType.RETIREMENT)
                .build();

        //then
        assertThat(product).isNotNull();
        assertThat(product2).isNotNull();
        assertThat(product).isNotEqualTo(product2);
    }

    @Test
    void productsWithSameId_willBeEquals() {
        //given
        UUID uuid = UUID.randomUUID();

        //when
        Product product = Product.builder()
                .id(uuid)
                .build();

        //and
        Product product2 = Product.builder()
                .id(uuid)
                .build();

        //then
        assertThat(product).isNotNull();
        assertThat(product2).isNotNull();
        assertThat(product).isEqualTo(product2);
    }
}