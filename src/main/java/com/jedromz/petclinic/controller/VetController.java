package com.jedromz.petclinic.controller;

import com.jedromz.petclinic.error.EntityNotFoundException;
import com.jedromz.petclinic.model.Pet;
import com.jedromz.petclinic.model.Vet;
import com.jedromz.petclinic.model.Visit;
import com.jedromz.petclinic.model.command.CreatePetCommand;
import com.jedromz.petclinic.model.command.CreateVetCommand;
import com.jedromz.petclinic.model.command.UpdatePetCommand;
import com.jedromz.petclinic.model.command.UpdateVetCommand;
import com.jedromz.petclinic.model.dto.PetDto;
import com.jedromz.petclinic.model.dto.VetDto;
import com.jedromz.petclinic.model.dto.VisitDto;
import com.jedromz.petclinic.service.VetService;
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
@RequestMapping("/vets")
@RequiredArgsConstructor
public class VetController {

    private final VetService vetService;
    private final ModelMapper modelMapper;

    @GetMapping("/{id}")
    public ResponseEntity<VetDto> getVet(@PathVariable long id) {
        return vetService.findById(id)
                .map(p -> modelMapper.map(p, VetDto.class))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new EntityNotFoundException("Vet", Long.toString(id)));
    }

    @GetMapping()
    public ResponseEntity<Page<VetDto>> getAllVets(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(vetService.findAll(pageable)
                .map(s -> modelMapper.map(s, VetDto.class)));
    }

    @PostMapping()
    public ResponseEntity<Vet> saveVet(@RequestBody CreateVetCommand command) {
        Vet vet = vetService.save(modelMapper.map(command, Vet.class));
        return new ResponseEntity(modelMapper.map(vet, VetDto.class), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteVet(@PathVariable long id) {
        vetService.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    @PutMapping("/{id}")
    public ResponseEntity<VetDto> editVet(@PathVariable long id, @RequestBody UpdateVetCommand command) {
        Vet toEdit = vetService.findById(id).orElseThrow(() -> new EntityNotFoundException("Vet", Long.toString(id)));
        Vet edited = vetService.edit(toEdit, command);
        return new ResponseEntity(modelMapper.map(edited, VetDto.class), HttpStatus.OK);
    }

    @GetMapping("/{id}/visits")
    public ResponseEntity<List<VisitDto>> getVetVisits(Long id) {
        return ResponseEntity.ok(vetService.findById(id).orElseThrow(() -> new EntityNotFoundException("Vet", Long.toString(id)))
                .getVisits()
                .stream()
                .map(visit -> modelMapper.map(visit, VisitDto.class))
                .toList());
    }


    @PutMapping("/{id}/fire")
    public ResponseEntity<VetDto> fireVet(@PathVariable long id) {
        Vet toFire = vetService.findById(id).orElseThrow(() -> new EntityNotFoundException("Vet", Long.toString(id)));
        Vet edited = vetService.fire(toFire);
        return new ResponseEntity(modelMapper.map(edited, VetDto.class), HttpStatus.OK);
    }
}
