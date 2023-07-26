package com.investment.domain.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class InvestorTest {

    @Test
    void investorsWithSameId_willBeEqual() {
        UUID id = UUID.randomUUID();

        //given
        Investor investor = Investor.builder()
                .dateOfBirth(LocalDate.of(2023, 7, 22))
                .id(id)
                .firstName("James")
                .lastName("Bond")
                .emailAddress("j@007.com")
                .build();

        Investor investor2 = Investor.builder()
                .dateOfBirth(LocalDate.of(1988, 7, 22))
                .id(id)
                .firstName("Steve")
                .lastName("Smith")
                .emailAddress("sm@sm.com")
                .build();

        assertThat(investor).isNotNull();
        assertThat(investor2).isNotNull();
        assertThat(investor).isEqualTo(investor2);
    }

    @Test
    void investorsWithDifferentIds_willNotBeEqual() {
        //given
        Investor investor = Investor.builder()
                .dateOfBirth(LocalDate.of(2023, 7, 22))
                .id(UUID.randomUUID())
                .firstName("James")
                .lastName("Bond")
                .emailAddress("j@007.com")
                .build();

        //and
        Investor investor2 = Investor.builder()
                .dateOfBirth(LocalDate.of(1988, 7, 22))
                .id(UUID.randomUUID())
                .firstName("Steve")
                .lastName("Smith")
                .emailAddress("sm@sm.com")
                .build();

        //then
        assertThat(investor).isNotNull();
        assertThat(investor2).isNotNull();
        assertThat(investor).isNotEqualTo(investor2);
    }
}