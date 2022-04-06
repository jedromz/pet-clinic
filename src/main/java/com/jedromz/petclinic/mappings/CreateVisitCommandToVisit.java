package com.jedromz.petclinic.mappings;

import com.jedromz.petclinic.error.EntityNotFoundException;
import com.jedromz.petclinic.model.Visit;
import com.jedromz.petclinic.model.command.CreateVisitCommand;
import com.jedromz.petclinic.service.PetService;
import com.jedromz.petclinic.service.VetService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateVisitCommandToVisit implements Converter<CreateVisitCommand, Visit> {

    private final PetService petService;
    private final VetService vetService;

    @Override
    public Visit convert(MappingContext<CreateVisitCommand, Visit> mappingContext) {
        CreateVisitCommand command = mappingContext.getSource();
        Visit visit = Visit.builder()
                .dateTime(command.getDateTime())
                .vet(vetService.findById(command.getVetId()).orElseThrow(() -> new EntityNotFoundException("Vet", Long.toString(command.getVetId()))))
                .pet(petService.findById(command.getPetId()).orElseThrow(() -> new EntityNotFoundException("Pet", Long.toString(command.getPetId()))))
                .build();
        return visit;
    }
}
