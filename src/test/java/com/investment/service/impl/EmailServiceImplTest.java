package com.investment.service.impl;

import com.investment.exceptions.EmailException;
import com.investment.model.EmailModel;
import com.investment.service.contract.EmailService;
import freemarker.template.Configuration;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceImplTest {

    @Mock
    JavaMailSender mailSender;

    Configuration configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);

    EmailService emailService = null;

    @BeforeEach
    void setUp() {
        emailService = new EmailServiceImpl(mailSender, configuration);
    }

    @Test
    void testWillSuccessfullySendEmail() {
        when(mailSender.createMimeMessage()).thenReturn(new JavaMailSenderImpl().createMimeMessage());
        emailService.send(EmailModel.builder()
                        .subject("Winter is here")
                        .toEmailAddress("js@got.com")
                        .fromEmailAddress("some@emal.com")
                .build(), "key");

        verify(mailSender, times(1)).send(any(MimeMessage.class));
    }

    @Test
    void testWillThrowException_whenEmailSubjectIsMissing() {
        when(mailSender.createMimeMessage()).thenReturn(new JavaMailSenderImpl().createMimeMessage());
        EmailModel model = EmailModel.builder()
                .toEmailAddress("js@got.com")
                .fromEmailAddress("some@emal.com")
                .build();

        assertThrows(EmailException.class, () -> emailService.send(model, "key"));
    }

    @Test
    void testWillThrowException_whenFromEmailIsMissing() {
        when(mailSender.createMimeMessage()).thenReturn(new JavaMailSenderImpl().createMimeMessage());
        EmailModel model = EmailModel.builder()
                .toEmailAddress("js@got.com")
                .subject("SUbj")
                .build();

        assertThrows(EmailException.class, () -> emailService.send(model, "key"));
    }

    @Test
    void testWillThrowException_whenToEmailIsMissing() {
        when(mailSender.createMimeMessage()).thenReturn(new JavaMailSenderImpl().createMimeMessage());
        EmailModel model = EmailModel.builder()
                .fromEmailAddress("fr@add.com")
                .subject("SUbj")
                .build();

        assertThrows(EmailException.class, () -> emailService.send(model, "key"));
    }

    @Test
    void testWillThrowException_whenBuildingTemplateBody() {
        when(mailSender.createMimeMessage()).thenReturn(new JavaMailSenderImpl().createMimeMessage());
        EmailModel emailModel = EmailModel.builder()
                .subject("Winter is here")
                .toEmailAddress("js@got.com")
                .fromEmailAddress("some@emal.com")
                .build();
        emailModel.addAttribute("firstName", "Jon");

        assertThrows(EmailException.class, () -> emailService.send(emailModel, "key"));
    }
}