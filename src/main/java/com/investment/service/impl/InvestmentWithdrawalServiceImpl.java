package com.investment.service.impl;

import com.investment.domain.entity.Investment;
import com.investment.domain.entity.Investor;
import com.investment.domain.repository.InvestmentRepository;
import com.investment.domain.repository.InvestorRepository;
import com.investment.events.model.WithdrawalProcessedEvent;
import com.investment.events.publisher.EventPublisher;
import com.investment.exceptions.InvalidWithdrawalRequestException;
import com.investment.exceptions.InvestorNotFoundException;
import com.investment.model.WithdrawalRequest;
import com.investment.model.WithdrawalResponse;
import com.investment.service.contract.InvestmentWithdrawalService;
import com.investment.utils.DateUtil;
import com.investment.utils.WithdrawalValidationCommand;
import com.investment.validators.withdrawal.WithdrawalValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS)
@Slf4j
public class InvestmentWithdrawalServiceImpl implements InvestmentWithdrawalService {

    private final InvestorRepository investorRepository;
    private final InvestmentRepository investmentRepository;
    private final List<WithdrawalValidator> withdrawalValidators;
    private final EventPublisher publisher;

    /**
     * @param request      WithdrawalRequest
     * @param investmentId unique InvestorIdentifier
     * @return WithdrawalResponse - with withdrawal response information
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public WithdrawalResponse processWithdrawal(WithdrawalRequest request, UUID investmentId) {
        log.info("Processing withdrawal request: {} for investment-product Id: {}", request, investmentId);

        Investment investment = investmentRepository.findById(investmentId).orElseThrow(() -> new InvalidWithdrawalRequestException(
                "invalid.withdrawal.request",
                String.format("Invalid withdrawal request for investment with given id: %s", investmentId)));

        final UUID investorId = investment.getInvestorId();
        Investor investor = investorRepository.findById(investorId)
                .orElseThrow(() -> new InvestorNotFoundException(investorId));

        WithdrawalValidationCommand command = WithdrawalValidationCommand.instanceOf(investor.getDateOfBirth(),
                investment.getBalance(),
                investment.getProductType(),
                request.getAmount());

        CollectionUtils.emptyIfNull(withdrawalValidators).forEach(validator -> validator.validate(command));

        final BigDecimal openingBalance = investment.getBalance();

        investment.withdraw(request.getAmount());
        investment = investmentRepository.save(investment);

        WithdrawalResponse response = WithdrawalResponse.builder()
                .reference(DateUtil.currentDateTimeInLongFormat())
                .newBalance(investment.getBalance())
                .build();

        fireWithdrawalEvent(investment, investor, openingBalance, response.getReference(), request.getAmount());

        return response;
    }

    private void fireWithdrawalEvent(Investment investment, Investor investor, BigDecimal openingBalance,
                                     String referenceNo, BigDecimal withdrawalAmount) {
        log.info("Publishing withdrawal event for withdrawal with reference {}", referenceNo);

        WithdrawalProcessedEvent event = WithdrawalProcessedEvent.instanceOf(investor.getFullName(),
                investor.getEmailAddress(),
                openingBalance, investment.getBalance(),
                referenceNo, investment.getCreatedDate(),
                withdrawalAmount);

        publisher.publish(event);
    }

}
