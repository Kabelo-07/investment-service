package com.investment.service.impl;

import com.investment.exceptions.EmailException;
import com.investment.model.EmailModel;
import com.investment.service.contract.EmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final Configuration configuration;

    @Override
    public void send(EmailModel emailModel, String templateKey) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setSubject(emailModel.getSubject());
            helper.setFrom(emailModel.getFromEmailAddress());
            helper.setTo(emailModel.getToEmailAddress());

            if (!emailModel.getAttributes().isEmpty()) {
                String content = buildEmailContent(emailModel.getAttributes(), templateKey);
                helper.setText(content, true);
            }

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            log.error("Error occurred when sending email", e);
            throw new EmailException(e.getMessage());
        }
    }

    private String buildEmailContent(Map<String, Object> attributes, String templateKey) {
        try {
            StringBuilder content = new StringBuilder();
            Template template = configuration.getTemplate(templateKey);
            content.append(FreeMarkerTemplateUtils.processTemplateIntoString(template, attributes));
            return content.toString();
        } catch (IOException | TemplateException e) {
            log.error("Error occurred building emailContent", e);
            throw new EmailException(e.getMessage());
        }
    }
}
