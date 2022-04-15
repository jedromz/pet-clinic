package com.jedromz.petclinic.controller;

import com.jedromz.petclinic.error.EntityNotFoundException;
import com.jedromz.petclinic.model.Pet;
import com.jedromz.petclinic.model.command.CreatePetCommand;
import com.jedromz.petclinic.model.command.UpdatePetCommand;
import com.jedromz.petclinic.model.dto.PetDto;
import com.jedromz.petclinic.model.dto.VisitDto;
import com.jedromz.petclinic.service.PetService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/pets")
@RequiredArgsConstructor
@CrossOrigin("*")
public class PetController {

    private final PetService petService;
    private final ModelMapper modelMapper;

    @GetMapping("/{id}")
    public ResponseEntity<PetDto> getPet(@PathVariable long id) {
        return petService.findById(id)
                .map(p -> modelMapper.map(p, PetDto.class))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new EntityNotFoundException("Pet", Long.toString(id)));
    }

    @GetMapping()
    public ResponseEntity<Page<PetDto>> getAllPets(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(petService.findAll(pageable)
                .map(s -> modelMapper.map(s, PetDto.class)));
    }

    @PostMapping()
    public ResponseEntity<Pet> savePet(@RequestBody @Valid CreatePetCommand command) {
        Pet pet = petService.save(modelMapper.map(command, Pet.class));
        return new ResponseEntity(modelMapper.map(pet, PetDto.class), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePet(@PathVariable long id) {
        petService.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    @PutMapping("/{id}")
    public ResponseEntity<PetDto> editPet(@PathVariable long id, @RequestBody UpdatePetCommand command) {
        Pet toEdit = petService.findById(id).orElseThrow(() -> new EntityNotFoundException("Pet", Long.toString(id)));
        Pet edited = petService.edit(toEdit, command);
        return new ResponseEntity(modelMapper.map(edited, PetDto.class), HttpStatus.OK);
    }

    @GetMapping("/{id}/visits")
    public ResponseEntity<List<VisitDto>> getPetVisits(@PathVariable Long id) {
        return ResponseEntity.ok(petService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pet", Long.toString(id)))
                .getVisits()
                .stream()
                .map(visit -> modelMapper.map(visit, VisitDto.class))
                .toList());
    }
}
