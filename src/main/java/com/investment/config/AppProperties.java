package com.investment.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "app")
@NoArgsConstructor
@Getter
@Setter
public class AppProperties {

    private int maxWithdrawalPercentage = 90;
    private int retirementWithdrawalAge = 65;
    private Map<String, EmailConfig> emailConfigs = new HashMap<>(0);


    @Getter
    @Setter
    public static class EmailConfig {
        private String subject;
        private String templateKey;
        private String fromEmail;
    }
}
