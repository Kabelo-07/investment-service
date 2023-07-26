package com.investment.domain.repository;

import com.investment.AbstractRepositoryTest;
import com.investment.domain.valueobjects.Address;
import com.investment.domain.entity.Investor;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InvestorRepositoryTest extends AbstractRepositoryTest<InvestorRepository> {

    @Test
    void willSaveNewInvestor() {
        //given
        Investor investor = Investor.builder()
                .address(Address.builder()
                        .addressLine1("123 Some Street")
                        .city("Sandton")
                        .suburb("Rivonia")
                        .province("Gauteng")
                        .postalCode("2001")
                        .build())
                .firstName("Jon")
                .lastName("Snow")
                .emailAddress("js@test.com")
                .mobileNumber("0889999999")
                .dateOfBirth(LocalDate.of(1988, 8, 1))
                .build();

        //when
        investor = entityManager.persistAndFlush(investor);

        //then
        Optional<Investor> investorOptional = repository.findById(investor.getId());
        assertThat(investorOptional).isPresent();
    }

    @Test
    void willSaveMoreThanOneInvestor() {
        //given: no investors exist
        assertThat(repository.count()).isEqualTo(0);

        //and
        Investor investor = Investor.builder()
                .address(Address.builder()
                        .addressLine1("123 Some Street")
                        .city("Sandton")
                        .suburb("Rivonia")
                        .province("Gauteng")
                        .postalCode("2001")
                        .build())
                .firstName("Jon")
                .lastName("Snow")
                .emailAddress("js@test.com")
                .mobileNumber("0889999999")
                .dateOfBirth(LocalDate.of(1988, 8, 1))
                .build();

        //when
        entityManager.persistAndFlush(investor);

        //and
        Investor investor2 = Investor.builder()
                .address(Address.builder()
                        .addressLine1("123 Some Street")
                        .city("Sandton")
                        .suburb("Rivonia")
                        .province("Gauteng")
                        .postalCode("2001")
                        .build())
                .firstName("Jonathan")
                .lastName("Wick")
                .emailAddress("jw@www.com")
                .mobileNumber("0881234567")
                .dateOfBirth(LocalDate.of(1979, 8, 23))
                .build();

        entityManager.persistAndFlush(investor2);

        //then
        assertThat(repository.count()).isEqualTo(2);
    }

    @Test
    void willThrowConstraintViolation_whenInvestorAgeIsLessThan18() {
        //given
        Investor investor = Investor.builder()
                .address(Address.builder().build())
                .firstName("Jon")
                .lastName("Snow")
                .emailAddress("js@test.com")
                .mobileNumber("0889999999")
                .dateOfBirth(LocalDate.of(Year.now().getValue(), Month.APRIL, 5))
                .build();

        //then
        assertThatThrownBy(() -> entityManager.persistAndFlush(investor))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("Invalid date of birth");
    }

    @Test
    void willThrowConstraintViolation_whenInvestorMandatoryInfo_isMissing() {
        //given
        Investor investor = Investor.builder()
                .address(Address.builder()
                        //.addressLine1("555 Simmons Street")
                        .city("Jhb")
                        .suburb("Jhb cbd")
                        .postalCode("2001")
                        .province("GP")
                        .build())
                .dateOfBirth(LocalDate.of(1988, Month.APRIL, 5))
                .build();

        //then
        assertThatThrownBy(() -> entityManager.persistAndFlush(investor))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void willThrowException_whenInvestorWithSameEmailExists() {
        //given
        Investor investor = Investor.builder()
                .address(Address.builder()
                        .addressLine1("123 Some Street")
                        .city("Sandton")
                        .suburb("Rivonia")
                        .province("Gauteng")
                        .postalCode("2001")
                        .build())
                .firstName("Jon")
                .lastName("Snow")
                .emailAddress("js@test.com")
                .mobileNumber("0889999999")
                .dateOfBirth(LocalDate.of(1988, 8, 1))
                .build();

        investor = entityManager.persistAndFlush(investor);

        //when
        Investor existingInvestor = Investor.builder()
                .address(Address.builder()
                        .addressLine1("123 Some Street")
                        .city("Sandton")
                        .suburb("Rivonia")
                        .province("Gauteng")
                        .postalCode("2001")
                        .build())
                .firstName("Jonathan")
                .lastName("Wick")
                .emailAddress("js@test.com")
                .mobileNumber("0991234567")
                .dateOfBirth(LocalDate.of(1978, 8, 1))
                .build();

        //then
        assertThatThrownBy(() -> entityManager.persistAndFlush(existingInvestor))
                .isInstanceOf(org.hibernate.exception.ConstraintViolationException.class)
                .hasMessageContaining("Unique index or primary key violation");
    }

    @Test
    void willThrowException_whenInvestorWithSameMobilePhoneNumberExists() {
        //given
        Investor investor = Investor.builder()
                .address(Address.builder()
                        .addressLine1("123 Some Street")
                        .city("Sandton")
                        .suburb("Rivonia")
                        .province("Gauteng")
                        .postalCode("2001")
                        .build())
                .firstName("Jon")
                .lastName("Snow")
                .emailAddress("js@test.com")
                .mobileNumber("0889999999")
                .dateOfBirth(LocalDate.of(1988, 8, 1))
                .build();

        entityManager.persistAndFlush(investor);

        //when
        Investor existingInvestor = Investor.builder()
                .address(Address.builder()
                        .addressLine1("123 Some Street")
                        .city("Sandton")
                        .suburb("Rivonia")
                        .province("Gauteng")
                        .postalCode("2001")
                        .build())
                .firstName("Jonathan")
                .lastName("Wick")
                .emailAddress("jw@www.com")
                .mobileNumber("0889999999")
                .dateOfBirth(LocalDate.of(1979, 8, 23))
                .build();

        //then
        assertThatThrownBy(() -> entityManager.persistAndFlush(existingInvestor))
                .isInstanceOf(org.hibernate.exception.ConstraintViolationException.class)
                .hasMessageContaining("Unique index or primary key violation");
    }

}