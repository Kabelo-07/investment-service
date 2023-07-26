package com.investment.service.impl;

import com.investment.domain.entity.Investor;
import com.investment.domain.repository.InvestorRepository;
import com.investment.exceptions.InvestorNotFoundException;
import com.investment.model.InvestorModel;
import com.investment.model.page.InvestorsPage;
import com.investment.service.contract.InvestorService;
import com.investment.utils.InvestorSpecificationBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

import static com.investment.TestData.buildNewInvestor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class InvestorServiceImplTest {

    @Autowired
    InvestorRepository investorRepository;

    @Autowired
    InvestorService investorService;

    @AfterEach
    public void cleanUp() {
        investorRepository.deleteAll();
    }

    @Test
    void findInvestorByIdWillReturnInvestor_whenInvestorWithIdExists() {

        //given
        Investor investor = buildNewInvestor("Jon", "Snow", "0891234567", "jon@snow.com");

        //when
        investor = investorRepository.save(investor);
        assertThat(investor.getCreatedDate()).isNotNull();
        assertThat(investor.getUpdatedDate()).isNotNull();

        //then
        InvestorModel model = investorService.findInvestorById(investor.getId());
        assertThat(model).isNotNull();
        assertThat(model.getId()).isEqualTo(investor.getId());
        assertThat(model.getFirstName()).isEqualTo(investor.getFirstName());
        assertThat(model.getLastName()).isEqualTo(investor.getLastName());
        assertThat(model.getDateOfBirth()).isEqualTo(investor.getDateOfBirth());
        //and
        assertThat(model.getContacts()).isNotNull();
        assertThat(model.getContacts().getEmailAddress()).isEqualTo(investor.getEmailAddress());
        assertThat(model.getContacts().getMobileNumber()).isEqualTo(investor.getMobileNumber());
        //and
        assertThat(model.getAddress()).isNotNull();
        assertThat(model.getAddress().getAddressLine1()).isEqualTo(investor.getAddress().getAddressLine1());
        assertThat(model.getAddress().getAddressLine2()).isEqualTo(investor.getAddress().getAddressLine2());
        assertThat(model.getAddress().getSuburb()).isEqualTo(investor.getAddress().getSuburb());
        assertThat(model.getAddress().getCity()).isEqualTo(investor.getAddress().getCity());
        assertThat(model.getAddress().getPostalCode()).isEqualTo(investor.getAddress().getPostalCode());
        assertThat(model.getAddress().getProvince()).isEqualTo(investor.getAddress().getProvince());
    }

    @Test
    void findInvestorById_willThrowInvestorNotFoundException_whenInvestorWithIdDoesNotExists() {

        //given
        UUID investorId = UUID.randomUUID();

        //then
        assertThatThrownBy(() -> investorService.findInvestorById(investorId))
                .isInstanceOf(InvestorNotFoundException.class)
                .hasMessage(String.format("Investor not found with given id: %s", investorId));
    }

    @Test
    void findAllWillReturnAllInvestors() {

        //given
        investorRepository.save(buildNewInvestor("James", "Bond", "0771231234", "jb@mi6-007.uk"));
        investorRepository.save(buildNewInvestor("Tony", "Stark", "0788990187", "tstark@avengers.com"));
        investorRepository.save(buildNewInvestor("Nick", "Fury", "0780094589", "nickf@shield.com"));

        //when
        InvestorsPage page = investorService.findAll(InvestorSpecificationBuilder.instance().build(), PageRequest.of(0, 5));

        //then
        assertThat(page).isNotNull();
        assertThat(page.getTotalElements()).isEqualTo(3L);
        assertThat(page.getContent()).hasSize(3);
    }

    @Test
    void findAllWillReturnInvestors_whenFilteredByInvestorId() {

        //given
        investorRepository.save(buildNewInvestor("James", "Bond", "0771231234", "jb@mi6-007.uk"));
        Investor investor = investorRepository.save(buildNewInvestor("Tony", "Stark", "0788990187", "tstark@avengers.com"));

        //and
        assertThat(investorRepository.count()).isEqualTo(2);

        //when
        InvestorsPage page = investorService.findAll(InvestorSpecificationBuilder.instance()
                .withInvestorId(investor.getId())
                .build(), PageRequest.of(0, 5));

        //then
        assertThat(page).isNotNull();
        assertThat(page.getTotalElements()).isEqualTo(1L);
        assertThat(page.getContent()).hasSize(1);
    }

    @Test
    void findAllWillReturnInvestors_whenFilteredByEmailAddress() {

        //given
        investorRepository.save(buildNewInvestor("James", "Bond", "0771231234", "jb@mi6-007.uk"));
        investorRepository.save(buildNewInvestor("Nick", "Fury", "0780094589", "nickf@avengers.com"));
        investorRepository.save(buildNewInvestor("Tony", "Stark", "0788990187", "tstark@avengers.com"));

        //and
        assertThat(investorRepository.count()).isEqualTo(3);

        //when
        InvestorsPage page = investorService.findAll(InvestorSpecificationBuilder.instance()
                .withEmailAddress("avengers.com")
                .build(), PageRequest.of(0, 5));

        //then
        assertThat(page).isNotNull();
        assertThat(page.getTotalElements()).isEqualTo(2L);
        assertThat(page.getContent()).hasSize(2);
    }

    @Test
    void findAllWillReturnInvestors_whenFilteredByPhoneNumber() {

        //given
        investorRepository.save(buildNewInvestor("James", "Bond", "0771231234", "jb@mi6-007.uk"));
        investorRepository.save(buildNewInvestor("Nick", "Fury", "0780094589", "nickf@avengers.com"));
        investorRepository.save(buildNewInvestor("Tony", "Stark", "0788990187", "tstark@avengers.com"));

        //and
        assertThat(investorRepository.count()).isEqualTo(3);

        //when
        InvestorsPage page = investorService.findAll(InvestorSpecificationBuilder.instance()
                .withMobileNumber("0780094589")
                .build(), PageRequest.of(0, 5));

        //then
        assertThat(page).isNotNull();
        assertThat(page.getTotalElements()).isEqualTo(1);
        assertThat(page.getContent()).hasSize(1);
    }

    @Test
    void findAllWillReturnInvestors_whenFilteredByFirstName() {

        //given
        investorRepository.save(buildNewInvestor("James", "Bond", "0771231234", "jb@mi6-007.uk"));
        investorRepository.save(buildNewInvestor("Nick", "Fury", "0780094589", "nickf@avengers.com"));
        investorRepository.save(buildNewInvestor("Tony", "Stark", "0788990187", "tstark@avengers.com"));

        //and
        assertThat(investorRepository.count()).isEqualTo(3);

        //when
        InvestorsPage page = investorService.findAll(InvestorSpecificationBuilder.instance()
                .withFirstName("James")
                .build(), PageRequest.of(0, 5));

        //then
        assertThat(page).isNotNull();
        assertThat(page.getTotalElements()).isEqualTo(1);
        assertThat(page.getContent()).hasSize(1);
    }

    @Test
    void findAllWillReturnInvestors_whenFilteredByLastName() {

        //given
        investorRepository.save(buildNewInvestor("James", "Bond", "0771231234", "jb@mi6-007.uk"));
        investorRepository.save(buildNewInvestor("Nick", "Fury", "0780094589", "nickf@avengers.com"));
        investorRepository.save(buildNewInvestor("Tony", "Stark", "0788990187", "tstark@avengers.com"));

        //and
        assertThat(investorRepository.count()).isEqualTo(3);

        //when
        InvestorsPage page = investorService.findAll(InvestorSpecificationBuilder.instance()
                .withLastName("Bond")
                .build(), PageRequest.of(0, 5));

        //then
        assertThat(page).isNotNull();
        assertThat(page.getTotalElements()).isEqualTo(1);
        assertThat(page.getContent()).hasSize(1);
    }

}