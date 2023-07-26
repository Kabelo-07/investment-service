package com.investment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.Objects;
import java.util.StringJoiner;

@Setter
@Getter
public class ContactModel {
    @JsonProperty("email_address")
    @Email
    @NotBlank
    private String emailAddress;

    @JsonProperty("mobile_number")
    @NotBlank
    private String mobileNumber;

    @Override
    public String toString() {
        return new StringJoiner(", ", ContactModel.class.getSimpleName() + "[", "]")
                .add("emailAddress='" + emailAddress + "'")
                .add("mobileNumber='" + mobileNumber + "'")
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContactModel that)) return false;
        return getEmailAddress().equals(that.getEmailAddress()) && getMobileNumber().equals(that.getMobileNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmailAddress(), getMobileNumber());
    }
}
