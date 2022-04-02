package com.jedromz.petclinic.model.command;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CreateVisitCommand {
    private Long id;
    private LocalDateTime dateTime;
    private Long vetId;
    private Long petId;
    private int version;
}
