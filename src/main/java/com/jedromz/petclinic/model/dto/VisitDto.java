package com.jedromz.petclinic.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class VisitDto {

    private LocalDateTime dateTime;
    private int version;
}
