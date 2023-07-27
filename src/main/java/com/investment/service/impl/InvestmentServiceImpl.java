package com.investment.service.impl;

import com.investment.domain.entity.Investment;
import com.investment.domain.repository.InvestmentRepository;
import com.investment.domain.repository.InvestorRepository;
import com.investment.mappers.InvestmentMapper;
import com.investment.model.InvestmentModel;
import com.investment.service.contract.InvestmentService;
import com.investment.validators.withdrawal.WithdrawalValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS)
public class InvestmentServiceImpl implements InvestmentService {

    private final InvestorRepository investorRepository;
    private final InvestmentRepository investmentRepository;
    private final List<WithdrawalValidator> withdrawalValidators;
    private final InvestmentMapper investmentMapper;

    /**
     * @param investorId unique InvestorIdentifier
     * @return List<InvestmentModel> - List of investments for a specific investor
     */
    @Override
    public List<InvestmentModel> findInvestmentsByInvestorId(UUID investorId) {
        List<Investment> products = investmentRepository.findByInvestorId(investorId);
        return investmentMapper.toModels(products);
    }

}
