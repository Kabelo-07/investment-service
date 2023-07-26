package com.investment.service.contract;

import com.investment.model.WithdrawalRequest;
import com.investment.model.WithdrawalResponse;

import java.util.UUID;

public interface InvestmentWithdrawalService {

    WithdrawalResponse processWithdrawal(WithdrawalRequest request, UUID investmentId);

}
