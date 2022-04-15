package com.jedromz.petclinic.mappings;

import com.jedromz.petclinic.model.Pet;
import com.jedromz.petclinic.model.command.CreatePetCommand;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

@Service
public class CreatePetCommandToPetConverter implements Converter<CreatePetCommand, Pet> {
    @Override
    public Pet convert(MappingContext<CreatePetCommand, Pet> mappingContext) {
        CreatePetCommand command = mappingContext.getSource();
        Pet pet = Pet.builder()
                .petName(command.getPetName())
                .birthdate(command.getBirthDate())
                .race(command.getRace())
                .type(command.getType())
                .ownerName(command.getOwnerName())
                .ownerEmail(command.getOwnerEmail())
                .build();
        return pet;
    }
}
