package com.jedromz.petclinic.mappings;

import com.jedromz.petclinic.controller.PetController;
import com.jedromz.petclinic.model.Pet;
import com.jedromz.petclinic.model.dto.PetDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class PetToPetDtoConverter implements Converter<Pet, PetDto> {

    @Override
    public PetDto convert(MappingContext<Pet, PetDto> mappingContext) {
        Pet pet = mappingContext.getSource();
        PetDto petDto = PetDto.builder()
                .petName(pet.getPetName())
                .type(pet.getType())
                .race(pet.getRace())
                .birthDate(pet.getBirthDate())
                .ownerEmail(pet.getOwnerEmail())
                .ownerName(pet.getOwnerName())
                .version(pet.getVersion())
                .build();
        petDto.add(linkTo(methodOn(PetController.class).getPet(pet.getId())).withRel("pet-details"));
        petDto.add(linkTo(methodOn(PetController.class).getPetVisits(pet.getId())).withRel("pet-visits"));
        return petDto;
    }
}
