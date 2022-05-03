package com.jedromz.petclinic.model.command;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.jedromz.petclinic.validation.annotation.FullHour;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;


import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateVisitCommand {
    @NotNull(message = "DATETIME_NOT_NULL")
    @Future
    @FullHour
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dateTime;
    @NotNull(message = "VET_ID_NOT_NULL")
    private Long vetId;
    @NotNull(message = "PET_ID_NOT_NULL")
    private Long petId;
}
