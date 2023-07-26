package com.investment.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.StringJoiner;
import java.util.UUID;

@Getter
@Setter
public class InvestorModel {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("first_name")
    @NotBlank
    private String firstName;

    @JsonProperty("last_name")
    @NotBlank
    private String lastName;

    @JsonProperty("date_of_birth")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotBlank
    private LocalDate dateOfBirth;

    @JsonProperty("address")
    @NotNull
    private AddressModel address;

    @JsonProperty("contacts")
    @NotNull
    private ContactModel contacts;

    @Override
    public String toString() {
        return new StringJoiner(", ", InvestorModel.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("firstName='" + firstName + "'")
                .add("lastName='" + lastName + "'")
                .add("dateOfBirth=" + dateOfBirth)
                .add("address=" + address)
                .add("contacts=" + contacts)
                .toString();
    }
}
