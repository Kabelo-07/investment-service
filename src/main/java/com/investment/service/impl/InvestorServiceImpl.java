package com.investment.service.impl;

import com.investment.domain.entity.Investor;
import com.investment.domain.repository.InvestmentRepository;
import com.investment.domain.repository.InvestorRepository;
import com.investment.exceptions.InvestorNotFoundException;
import com.investment.mappers.InvestorMapper;
import com.investment.model.*;
import com.investment.model.page.InvestorsPage;
import com.investment.service.contract.InvestorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS)
public class InvestorServiceImpl implements InvestorService {

    private final InvestorRepository investorRepository;
    private final InvestorMapper investorMapper;

    /**
     *
     * @param id unique investorId
     * @return investorModel - model with investor information
     */
    @Override
    public InvestorModel findInvestorById(UUID id) {
        Investor investor = investorRepository
                .findById(id)
                .orElseThrow(() -> new InvestorNotFoundException(id));
        return investorMapper.toModel(investor);
    }

    /**
     *
     * @param specification jpa Specification
     * @param pageRequest Pageable data with pageNo and pageSize
     * @return InvestorsPage -> a Pageable object with investment information content
     */
    @Override
    public InvestorsPage findAll(Specification<Investor> specification, PageRequest pageRequest) {
        Page<Investor> investorPage = investorRepository.findAll(specification, pageRequest);
        return InvestorsPage.toPage(investorPage);
    }

}
