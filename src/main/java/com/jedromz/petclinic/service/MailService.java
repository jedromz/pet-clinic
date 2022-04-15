package com.jedromz.petclinic.service;

import com.jedromz.petclinic.model.dto.NotificationEmail;
import org.springframework.scheduling.annotation.Async;

public interface MailService {
    @Async
    void sendMail(NotificationEmail notificationEmail);
}
