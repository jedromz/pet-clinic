package com.jedromz.petclinic.model.command;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class UpdateVisitCommand {
    @NotNull(message = "DATETIME_NOT_NULL")
    private LocalDateTime dateTime;
    @NotNull(message = "VERSION_NOT_EMPTYe")
    private int version;
}
