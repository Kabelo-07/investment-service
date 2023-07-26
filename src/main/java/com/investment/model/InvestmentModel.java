package com.investment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.investment.utils.AmountSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.StringJoiner;
import java.util.UUID;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
public class InvestmentModel {

    @JsonProperty("investment_product_id")
    private UUID id;

    @JsonProperty("balance")
    @JsonSerialize(using = AmountSerializer.class)
    private BigDecimal balance;

    @JsonProperty("product")
    private ProductModel product;

    @Override
    public String toString() {
        return new StringJoiner(", ", InvestmentModel.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("balance=" + balance)
                .add("product=" + product)
                .toString();
    }
}
