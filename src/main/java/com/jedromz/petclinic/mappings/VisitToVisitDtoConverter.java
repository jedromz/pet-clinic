package com.jedromz.petclinic.mappings;

import com.jedromz.petclinic.controller.PetController;
import com.jedromz.petclinic.controller.VetController;
import com.jedromz.petclinic.controller.VisitController;
import com.jedromz.petclinic.model.Visit;
import com.jedromz.petclinic.model.dto.VisitDto;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class VisitToVisitDtoConverter implements Converter<Visit, VisitDto> {
    @Override
    public VisitDto convert(MappingContext<Visit, VisitDto> mappingContext) {
        Visit visit = mappingContext.getSource();
        VisitDto visitDto = VisitDto.builder()
                .id(visit.getId())
                .dateTime(visit.getDateTime())
                .version(visit.getVersion())
                .build();
        visitDto.add(linkTo(methodOn(VisitController.class).getVisit(visit.getId())).withRel("visit-details"));
        visitDto.add(linkTo(methodOn(VetController.class).getVet(visit.getVet().getId())).withRel("vet-details"));
        visitDto.add(linkTo(methodOn(PetController.class).getPet(visit.getPet().getId())).withRel("pet-visits"));
        return visitDto;
    }
}
