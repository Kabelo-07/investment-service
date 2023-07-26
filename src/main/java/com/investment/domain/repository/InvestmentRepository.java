package com.investment.domain.repository;

import com.investment.domain.entity.Investment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface InvestmentRepository extends JpaRepository<Investment, UUID> {

    List<Investment> findByInvestorId(UUID investorId);
}
