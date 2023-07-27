package com.investment.mappers;

import com.investment.domain.entity.Investor;
import com.investment.domain.valueobjects.Address;
import com.investment.model.InvestorModel;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class InvestorMapperTest {

    final InvestorMapper investorMapper = InvestorMapper.INSTANCE;

    @Test
    void toModel() {
        Investor investor = getInvestor();

        InvestorModel model = investorMapper.toModel(investor);

        assertNotNull(model);
        assertEquals(model.getFirstName(), investor.getFirstName());
        assertEquals(model.getLastName(), investor.getLastName());
        assertEquals(model.getContacts().getEmailAddress(), investor.getEmailAddress());
        assertEquals(model.getContacts().getMobileNumber(), investor.getMobileNumber());
        assertEquals(model.getDateOfBirth(), investor.getDateOfBirth());
        assertEquals(model.getAddress().getAddressLine1(), investor.getAddress().getAddressLine1());
        assertEquals(model.getAddress().getAddressLine2(), investor.getAddress().getAddressLine2());
        assertEquals(model.getAddress().getCity(), investor.getAddress().getCity());
        assertEquals(model.getAddress().getSuburb(), investor.getAddress().getSuburb());
        assertEquals(model.getAddress().getProvince(), investor.getAddress().getProvince());
        assertEquals(model.getAddress().getPostalCode(), investor.getAddress().getPostalCode());
    }

    private Investor getInvestor() {
        return Investor.builder()
                .dateOfBirth(LocalDate.of(2002, Month.JANUARY, 12))
                .mobileNumber("0191239000")
                .emailAddress("asd@email.com")
                .id(UUID.randomUUID())
                .firstName("User")
                .lastName("User Two")
                .address(Address.builder()
                        .city("City")
                        .addressLine1("555 Line 1")
                        .addressLine2("Jhb")
                        .city("Jhb C")
                        .suburb("Sub")
                        .postalCode("0111")
                        .province("GP")
                        .build())
                .build();
    }

    @Test
    void toModels() {
        List<InvestorModel> models = investorMapper.toModels(List.of(getInvestor()));

        assertNotNull(models);
        assertFalse(models.isEmpty());
    }

}