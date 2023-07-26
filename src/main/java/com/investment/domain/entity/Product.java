package com.investment.domain.entity;

import com.investment.domain.valueobjects.ProductType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;

import java.util.StringJoiner;

@Table(name = "products", uniqueConstraints = {
        @UniqueConstraint(name = "uq_products_product_name", columnNames = {"product_name"})
})
@Entity
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Getter
public class Product extends AbstractEntity {

    @Column(name = "product_name", unique = true, nullable = false)
    @NotBlank(message = "productName must not be blank")
    private String productName;

    @Column(name = "product_type", nullable = false)
    @NotNull(message = "productType must be provided")
    @Enumerated(EnumType.STRING)
    private ProductType productType;

    @PrePersist
    @PreUpdate
    public void prePersist() {
        this.productName = StringUtils.capitalize(StringUtils.lowerCase(this.productName));
        this.productName = StringUtils.trim(productName);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Product.class.getSimpleName() + "[", "]")
                .add("productName='" + productName + "'")
                .add("productType=" + productType)
                .add("id=" + id)
                .toString();
    }
}
