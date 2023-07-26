package com.investment.domain.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.util.StringJoiner;

@Embeddable
@SuperBuilder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address implements Serializable {

    @Serial
    private static final long serialVersionUID = 1905122041950251207L;

    @Column(name = "address_line1", nullable = false)
    @Size(min = 5)
    private String addressLine1;

    @Column(name = "address_line2")
    @Size(min = 5)
    private String addressLine2;

    @Column(name = "suburb", nullable = false)
    @NotBlank
    @Size(min = 3)
    private String suburb;

    @Column(nullable = false)
    @NotBlank
    @Size(min = 3)
    private String city;

    @Column(nullable = false)
    @NotBlank
    @Size(min = 5)
    private String province;

    @Column(name = "postal_code", nullable = false)
    @NotBlank
    @Size(min = 5)
    private String postalCode;

    @Override
    public String toString() {
        return new StringJoiner(", ", Address.class.getSimpleName() + "[", "]")
                .add("addressLine1='" + addressLine1 + "'")
                .add("addressLine2='" + addressLine2 + "'")
                .add("suburb='" + suburb + "'")
                .add("city='" + city + "'")
                .add("province='" + province + "'")
                .add("postalCode='" + postalCode + "'")
                .toString();
    }
}
