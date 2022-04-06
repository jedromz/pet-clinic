package com.jedromz.petclinic.service;

import com.jedromz.petclinic.model.NotificationEmail;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;

public interface MailService {
    @Async
    void sendMail(NotificationEmail notificationEmail);
}
