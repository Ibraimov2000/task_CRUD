package com.todoapi.task.service;

import com.todoapi.task.entity.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    @Value("${email.recipient.login}")
    private String RECIPIENT_EMAIL;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendTaskCreationEmail(Task task) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(RECIPIENT_EMAIL);
            helper.setSubject("New Task created");
            helper.setText("A new task has been created: " + task.getDescription());

            mailSender.send(message);
            log.info("Task creation email sent successfully");
        } catch (MessagingException e) {
            log.error("Failed to send email", e);
        }
    }
}
