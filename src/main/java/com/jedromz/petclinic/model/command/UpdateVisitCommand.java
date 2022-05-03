package com.jedromz.petclinic.model.command;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateVisitCommand {
    @NotNull(message = "DATETIME_NOT_NULL")
    private LocalDateTime dateTime;
    @NotNull(message = "VERSION_NOT_EMPTYe")
    private int version;
}
