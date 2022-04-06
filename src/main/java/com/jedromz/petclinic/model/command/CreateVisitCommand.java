package com.jedromz.petclinic.model.command;


import com.jedromz.petclinic.validation.annotation.FullHour;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CreateVisitCommand {

    @NotNull(message = "DATETIME_NOT_NULL")
    @Future
    @FullHour
    private LocalDateTime dateTime;
    @NotNull(message = "VET_ID_NOT_NULL")
    private Long vetId;
    @NotNull(message = "PET_ID_NOT_NULL")
    private Long petId;
}
