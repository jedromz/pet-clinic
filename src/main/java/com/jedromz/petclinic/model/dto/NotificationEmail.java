package com.jedromz.petclinic.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationEmail {
    private String sender;
    private String subject;
    private String recipient;
    private String body;
}
