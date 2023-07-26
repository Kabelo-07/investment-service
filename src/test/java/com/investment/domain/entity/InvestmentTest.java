package com.investment.domain.entity;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class InvestmentTest {

    @Test
    void investmentWithSameId_willBeEqual() {
        UUID id = UUID.randomUUID();

        //given
        Investment investment = Investment.builder()
                .investorId(UUID.randomUUID())
                .id(id)
                .build();

        //and
        Investment investment2 = Investment.builder()
                .investorId(UUID.randomUUID())
                .id(id)
                .build();

        assertThat(investment).isNotNull();
        assertThat(investment2).isNotNull();
        assertThat(investment).isEqualTo(investment2);
    }

    @Test
    void investmentsWithDifferentIds_willNotBeEqual() {
        //given
        Investment investment = Investment.builder()
                .investorId(UUID.randomUUID())
                .id(UUID.randomUUID())
                .build();

        //and
        Investment investment2 = Investment.builder()
                .investorId(UUID.randomUUID())
                .id(UUID.randomUUID())
                .build();

        //then
        assertThat(investment).isNotNull();
        assertThat(investment2).isNotNull()
                .isNotEqualTo(investment);
    }
}