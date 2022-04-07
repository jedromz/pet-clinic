package com.jedromz.petclinic.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class VisitDto extends RepresentationModel<VisitDto> {

    private Long id;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime dateTime;
    private boolean confirmed;
    private int version;
}
