package com.investment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.investment.utils.AmountSerializer;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.StringJoiner;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class WithdrawalRequest {

    @NotNull
    @JsonSerialize(using = AmountSerializer.class)
    @Min(value = 100, message = "minimum withdrawal amount is 100 ZAR")
    @JsonProperty("withdrawal_amount")
    private BigDecimal amount;

    @NotNull(message = "banking details must be provided")
    @JsonProperty("banking_details")
    private BankingDetail bankingDetails;

    @Override
    public String toString() {
        return new StringJoiner(", ", WithdrawalRequest.class.getSimpleName() + "[", "]")
                .add("amount=" + amount)
                .add("bankingDetail=" + bankingDetails)
                .toString();
    }
}
