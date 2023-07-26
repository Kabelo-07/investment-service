package com.investment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.investment.utils.AmountSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class WithdrawalResponse {

    @JsonProperty("reference")
    private String reference;

    @JsonProperty("message")
    private String message;

    @JsonProperty("new_balance")
    @JsonSerialize(using = AmountSerializer.class)
    private BigDecimal newBalance;

}
