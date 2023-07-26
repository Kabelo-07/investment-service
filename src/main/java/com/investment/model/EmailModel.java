package com.investment.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@SuperBuilder
public class EmailModel {

    @Email
    @NotBlank
    private String fromEmailAddress;

    @Email
    @NotBlank
    private String toEmailAddress;

    @NotBlank
    private String subject;

    private final Map<String, Object> attributes = new HashMap<>(1);

    public void addAttribute(String key, String value) {
        attributes.put(key, value);
    }

    public void addAttributes(Map<String, Object> attributes) {
        this.attributes.putAll(attributes);
    }
}
