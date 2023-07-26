package com.investment.service.contract;

import com.investment.domain.entity.Investor;
import com.investment.model.InvestorModel;
import com.investment.model.page.InvestorsPage;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public interface InvestorService {

    InvestorModel findInvestorById(UUID id);

    InvestorsPage findAll(Specification<Investor> specification, PageRequest pageRequest);
}
