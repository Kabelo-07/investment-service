package com.investment.events.listener;

import com.investment.config.AppProperties;
import com.investment.events.model.WithdrawalProcessedEvent;
import com.investment.model.EmailModel;
import com.investment.service.contract.EmailService;
import com.investment.utils.DateTimeUtil;
import com.investment.utils.MoneyUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class WithdrawalProcessedEventListener {

    private final EmailService emailService;
    private final AppProperties appProperties;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(WithdrawalProcessedEvent event) {
        log.info("Handling event {}", event);

        String lowerCaseEventType = StringUtils.lowerCase(event.eventType().name());
        AppProperties.EmailConfig emailConfig = appProperties.getEmailConfigs().get(lowerCaseEventType);

        EmailModel model = EmailModel.builder()
                .fromEmailAddress(emailConfig.getFromEmail())
                .toEmailAddress(event.getInvestorEmail())
                .subject(emailConfig.getSubject())
                .build();

        Map<String, Object> map = new HashMap<>();
        map.put("fullName", event.getInvestorName());
        map.put("openingBalance", MoneyUtil.formatToCurrency(event.getOpeningBalance()));
        map.put("closingBalance", MoneyUtil.formatToCurrency(event.getClosingBalance()));
        map.put("withdrawalAmount", MoneyUtil.formatToCurrency(event.getWithdrawalAmount()));
        map.put("dateProcessed", DateTimeUtil.formatToShortDateTime(event.getDateProcessed()));
        map.put("referenceNo", event.getReferenceNo());

        model.addAttributes(map);

        log.info("Sending email for event {}, using template {}", event.getId(), emailConfig.getTemplateKey());
        emailService.send(model, emailConfig.getTemplateKey());
    }
}
