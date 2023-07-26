package com.investment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.StringJoiner;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class BankingDetail {

    @JsonProperty("account_holder_name")
    @NotBlank
    @Size(min = 5)
    private String accountHolderName;

    @JsonProperty("account_number")
    @Pattern(regexp = "^\\d{10}", message = "Invalid account number provided")
    @NotBlank
    private String accountNumber;

    @JsonProperty("bank_name")
    @NotBlank
    private String bankName;

    @JsonProperty("branch_code")
    @NotBlank
    @Pattern(regexp = "^\\d{6}", message = "Invalid branch code")
    private String branchCode;

    @Override
    public String toString() {
        return new StringJoiner(", ", BankingDetail.class.getSimpleName() + "[", "]")
                .add("accountHolderName='" + accountHolderName + "'")
                .add("bankName='" + bankName + "'")
                .toString();
    }
}
