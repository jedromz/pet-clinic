package com.jedromz.petclinic.service.implementation;

import com.jedromz.petclinic.model.dto.NotificationEmail;
import com.jedromz.petclinic.service.MailService;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;

    @Async
    public void sendMail(NotificationEmail notificationEmail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(notificationEmail.getSender());
        mailMessage.setTo(notificationEmail.getRecipient());
        mailMessage.setSubject(notificationEmail.getSubject());
        mailMessage.setText(notificationEmail.getBody());

        mailSender.send(mailMessage);
    }
}
