package com.investment.domain.entity;

import com.investment.domain.valueobjects.Address;
import com.investment.validators.DateOfBirth;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.StringJoiner;

@Entity
@Table(name = "investors", uniqueConstraints = {
        @UniqueConstraint(name = "uq_investors_email_address", columnNames = {"email_address"}),
        @UniqueConstraint(name = "uq_investors_mobile_number", columnNames = {"mobile_number"})
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Getter
public class Investor extends AbstractEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 2405172041950251807L;

    @Column(nullable = false, name = "first_name")
    @NotBlank(message = "firstName must not be blank")
    @Size(min = 2)
    private String firstName;

    @Column(nullable = false, name = "last_name")
    @NotBlank(message = "lastName must not be blank")
    @Size(min = 2)
    private String lastName;

    @Column(nullable = false, name = "date_of_birth")
    @DateOfBirth
    private LocalDate dateOfBirth;

    @Column(nullable = false, name = "mobile_number", unique = true)
    @NotBlank(message = "mobileNumber must not be blank")
    @Size(min = 10, max = 14, message = "mobileNumber must have a minimum value of 10 digits")
    @Pattern(regexp = "^\\d{10}")
    private String mobileNumber;

    @Embedded
    @NotNull(message = "address is required")
    private Address address;

    @Email(message = "invalid email address")
    @NotBlank(message = "emailAddress must not be blank")
    @Column(nullable = false, name = "email_address", unique = true)
    private String emailAddress;

    @PrePersist
    @PreUpdate
    public void prePersist() {
        this.emailAddress = StringUtils.lowerCase(StringUtils.trim(this.emailAddress));
        this.mobileNumber = StringUtils.trim(this.mobileNumber);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Investor.class.getSimpleName() + "[", "]")
                .add("firstName='" + firstName + "'")
                .add("lastName='" + lastName + "'")
                .add("dateOfBirth=" + dateOfBirth)
                .add("mobileNumber='" + mobileNumber + "'")
                .add("address=" + address)
                .add("emailAddress='" + emailAddress + "'")
                .add("id=" + id)
                .toString();
    }

    public String getFullName() {
        return String.format("%s %s", getFirstName(), getLastName());
    }
}
