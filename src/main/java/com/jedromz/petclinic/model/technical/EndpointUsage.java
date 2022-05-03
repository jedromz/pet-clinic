package com.jedromz.petclinic.model.technical;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class EndpointUsage {
    @Id
    @GeneratedValue
    private int id;

    private LocalDateTime executionDate;
    private String ipAddress;
    private long executionTime;
    private String methodName;

}
