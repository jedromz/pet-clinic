package com.jedromz.petclinic.mappings;

import com.jedromz.petclinic.controller.PetController;
import com.jedromz.petclinic.controller.VetController;
import com.jedromz.petclinic.model.Vet;
import com.jedromz.petclinic.model.dto.VetDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class VetToVetDtoConverter implements Converter<Vet, VetDto> {
    @Override
    public VetDto convert(MappingContext<Vet, VetDto> mappingContext) {
        Vet vet = mappingContext.getSource();
        VetDto vetDto = VetDto.builder()
                .firstName(vet.getFirstName())
                .lastName(vet.getLastName())
                .petSpecialization(vet.getPetSpecialization())
                .specialization(vet.getSpecialization())
                .rate(vet.getRate())
                .nip(vet.getNip())
                .isFired(vet.isFired())
                .version(vet.getVersion())
                .build();
        vetDto.add(linkTo(methodOn(VetController.class).getVet(vet.getId())).withRel("vet-details"));
        vetDto.add(linkTo(methodOn(VetController.class).getVetVisits(vet.getId())).withRel("vet-visits"));
        return vetDto;
    }
}
