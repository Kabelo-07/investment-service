package com.investment.events.listener;

import com.investment.config.AppProperties;
import com.investment.events.model.WithdrawalProcessedEvent;
import com.investment.service.contract.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WithdrawalProcessedEventListenerTest {

    WithdrawalProcessedEventListener listener;

    @Mock
    EmailService emailService;

    @Mock
    AppProperties properties;

    @BeforeEach
    void setUp() {
        listener = new WithdrawalProcessedEventListener(emailService, properties);
    }

    @Test
    void testWillHandleWithdrawalEventSuccessfully() {
        //given
        AppProperties.EmailConfig emailConfig = new AppProperties.EmailConfig();
        emailConfig.setFromEmail("from@mail.com");
        emailConfig.setSubject("Some subject");
        emailConfig.setTemplateKey("withdrawal_key");

        //and
        when(properties.getEmailConfigs()).thenReturn(Map.of("withdrawal", emailConfig));
        WithdrawalProcessedEvent event = WithdrawalProcessedEvent.instanceOf("Steve Austin", "stonecold@wwe.com",
                BigDecimal.valueOf(24000), BigDecimal.valueOf(23500), UUID.randomUUID().toString(),
                Instant.now(), BigDecimal.valueOf(500));

        //when
        listener.handle(event);

        //then
        verify(emailService, times(1)).send(any(), any());
    }

    @Test
    void testWillNotSendEmail_whenHandleWithdrawalEventFails() {
        //given
        AppProperties.EmailConfig emailConfig = new AppProperties.EmailConfig();
        emailConfig.setFromEmail("from@mail.com");
        emailConfig.setSubject("Some subject");
        emailConfig.setTemplateKey("withdrawal_key");

        //and
        when(properties.getEmailConfigs()).thenReturn(Map.of("withdrawals", emailConfig));
        WithdrawalProcessedEvent event = WithdrawalProcessedEvent.instanceOf("Steve Austin", "stonecold@wwe.com",
                null, BigDecimal.valueOf(23500), UUID.randomUUID().toString(),
                Instant.now(), BigDecimal.valueOf(500));

        //when
        assertThrows(Exception.class, () -> listener.handle(event));

        //then
        verify(emailService, never()).send(any(), any());
    }
}