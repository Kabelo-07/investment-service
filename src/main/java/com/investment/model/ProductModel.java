package com.investment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.StringJoiner;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
public class ProductModel {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("name")
    private String productName;

    @JsonProperty("type")
    private ProductTypeModel productType;

    @Override
    public String toString() {
        return new StringJoiner(", ", ProductModel.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("productName='" + productName + "'")
                .add("productType='" + productType + "'")
                .toString();
    }
}
