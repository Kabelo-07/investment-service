package com.investment;

import com.investment.domain.entity.Investment;
import com.investment.domain.entity.Product;
import com.investment.domain.valueobjects.ProductType;
import com.investment.domain.repository.InvestmentRepository;
import com.investment.domain.repository.ProductRepository;
import com.investment.events.publisher.EventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * <>This a helper class with common test methods used across investment controller and services</>
 */
public class AbstractInvestmentTestHelper {

    @Autowired
    protected InvestmentRepository investmentRepository;

    @Autowired
    protected ProductRepository productRepository;

    @MockBean
    protected EventPublisher eventPublisher;

    public Investment createInvestment(Product product, BigDecimal balance, UUID investorId) {
        Investment investment = Investment.builder()
                .investorId(investorId)
                .product(product)
                .balance(balance)
                .build();

        return investmentRepository.save(investment);
    }

    public Product createProduct(ProductType productType) {
        Product product = Product.builder()
                .productType(productType)
                .productName(String.format("Classic - %s", productType))
                .build();
        return productRepository.save(product);
    }
}
