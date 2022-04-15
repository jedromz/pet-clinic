package com.jedromz.petclinic.model.command;

import lombok.Value;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

@Value
public class CheckVisitsCommand {
    @Nullable
    private LocalDateTime fromDate;
    @Nullable
    private LocalDateTime toDate;
    @Nullable
    private String specialization;
    @Nullable
    private String petSpecialization;
}
