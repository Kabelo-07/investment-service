package com.investment.domain.entity;

import com.investment.domain.valueobjects.ProductType;
import com.investment.utils.MoneyUtil;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.UUID;

@Entity
@Table(name = "investment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Getter
public class Investment extends AbstractEntity {

    @Column(nullable = false, name = "investor_id")
    @NotNull(message = "investorId must be provided")
    private UUID investorId;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false, name = "product_id", referencedColumnName = "id")
    @NotNull(message = "product must be provided")
    private Product product;

    @Column(nullable = false, name = "balance", precision = 10, scale = 2)
    @NotNull
    private BigDecimal balance = BigDecimal.ZERO;

    @Version
    @Column(nullable = false)
    @NotNull
    private Long version = 1L;

    public String getProductName() {
        return Optional.ofNullable(this.getProduct())
                .map(Product::getProductName)
                .orElse(null);
    }

    public ProductType getProductType() {
        return Optional.ofNullable(this.getProduct())
                .map(Product::getProductType)
                .orElse(null);
    }

    public BigDecimal getBalance() {
        return MoneyUtil.roundedAmount(balance, 2);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Investment.class.getSimpleName() + "[", "]")
                .add("investorId=" + investorId)
                .add("product=" + product)
                .add("balance=" + balance)
                .add("id=" + id)
                .toString();
    }

    public void withdraw(BigDecimal amount) {
        this.balance = balance.subtract(amount);
    }
}
