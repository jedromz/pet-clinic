package com.jedromz.petclinic.service.implementation;

import com.jedromz.petclinic.config.EmailConfiguration;
import com.jedromz.petclinic.model.NotificationEmail;
import com.jedromz.petclinic.service.MailService;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;
    private final MailContentBuilder mailContentBuilder;
    private final EmailConfiguration emailCfg;

    @Async
    public void sendMail(NotificationEmail notificationEmail) {
        // Create a mail sender
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(this.emailCfg.getHost());
        mailSender.setPort(this.emailCfg.getPort());
        mailSender.setUsername(this.emailCfg.getUsername());
        mailSender.setPassword(this.emailCfg.getPassword());

        // Create an email instance
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(notificationEmail.getSender());
        mailMessage.setTo(notificationEmail.getRecipient());
        mailMessage.setSubject(notificationEmail.getSubject());
        mailMessage.setText(notificationEmail.getBody());

        // Send mail
        mailSender.send(mailMessage);
    }


}
