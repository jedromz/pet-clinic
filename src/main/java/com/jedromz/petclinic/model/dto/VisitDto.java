package com.jedromz.petclinic.model.dto;

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
    private LocalDateTime dateTime;
    private int version;
}
