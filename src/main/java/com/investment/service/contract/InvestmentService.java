package com.investment.service.contract;

import com.investment.model.InvestmentModel;

import java.util.List;
import java.util.UUID;

public interface InvestmentService {

    List<InvestmentModel> findInvestmentsByInvestorId(UUID investorId);

}
