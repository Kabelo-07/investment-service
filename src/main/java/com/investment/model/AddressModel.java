package com.investment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;
import java.util.Objects;
import java.util.StringJoiner;

@Getter
@Setter
public class AddressModel {

    @JsonProperty("address_line1")
    @NotBlank
    private String addressLine1;

    @JsonProperty("address_line2")
    private String addressLine2;

    @JsonProperty("suburb")
    @NotBlank
    private String suburb;

    @JsonProperty("city")
    @NotBlank
    private String city;

    @JsonProperty("province")
    @NotBlank
    private String province;

    @JsonProperty("postal_code")
    @NotBlank
    private String postalCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AddressModel that)) return false;
        return getAddressLine1().equals(that.getAddressLine1()) && Objects.equals(getAddressLine2(), that.getAddressLine2()) && getSuburb().equals(that.getSuburb()) && getCity().equals(that.getCity()) && getProvince().equals(that.getProvince()) && getPostalCode().equals(that.getPostalCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAddressLine1(), getAddressLine2(), getSuburb(), getCity(), getProvince(), getPostalCode());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AddressModel.class.getSimpleName() + "[", "]")
                .add("addressLine1='" + addressLine1 + "'")
                .add("addressLine2='" + addressLine2 + "'")
                .add("suburb='" + suburb + "'")
                .add("city='" + city + "'")
                .add("province='" + province + "'")
                .add("postalCode='" + postalCode + "'")
                .toString();
    }
}
