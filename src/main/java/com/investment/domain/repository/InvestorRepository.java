package com.investment.domain.repository;

import com.investment.domain.entity.Investor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InvestorRepository extends JpaRepository<Investor, UUID>, JpaSpecificationExecutor<Investor> { }
