package com.jedromz.petclinic.mappings;

import com.jedromz.petclinic.model.Vet;
import com.jedromz.petclinic.model.dto.VetDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

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
                .version(vet.getVersion())
                .build();
        return vetDto;
    }
}
