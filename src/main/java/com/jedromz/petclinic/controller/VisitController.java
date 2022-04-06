package com.jedromz.petclinic.controller;

import com.jedromz.petclinic.error.EntityNotFoundException;
import com.jedromz.petclinic.model.Visit;
import com.jedromz.petclinic.model.VisitToken;
import com.jedromz.petclinic.model.command.CreateVisitCommand;
import com.jedromz.petclinic.model.command.UpdateVisitCommand;
import com.jedromz.petclinic.model.dto.VisitDto;
import com.jedromz.petclinic.service.VisitService;
import com.jedromz.petclinic.service.implementation.VisitTokenService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/visits")
@RequiredArgsConstructor
public class VisitController {

    private final VisitService visitService;
    private final VisitTokenService visitTokenService;
    private final ModelMapper modelMapper;

    @GetMapping("/{id}")
    public ResponseEntity<VisitDto> getVisit(@PathVariable long id) {
        return visitService.findById(id)
                .map(p -> modelMapper.map(p, VisitDto.class))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new EntityNotFoundException("Visit", Long.toString(id)));
    }

    @GetMapping()
    public ResponseEntity<Page<VisitDto>> getAllVisits(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(visitService.findAll(pageable)
                .map(s -> modelMapper.map(s, VisitDto.class)));
    }

    @PostMapping()
    public ResponseEntity<VisitDto> saveVisit(@RequestBody CreateVisitCommand command) {
        Visit visit = visitService.save(modelMapper.map(command, Visit.class));
        return new ResponseEntity<>(modelMapper.map(visit, VisitDto.class), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<VisitDto> deleteVisit(@PathVariable long id) {
        visitService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VisitDto> editVisit(@PathVariable long id, @RequestBody UpdateVisitCommand command) {
        Visit toEdit = visitService.findById(id).orElseThrow(() -> new EntityNotFoundException("Visit", Long.toString(id)));
        Visit edited = visitService.edit(toEdit, command);
        return new ResponseEntity<>(modelMapper.map(edited, VisitDto.class), HttpStatus.OK);
    }

    @GetMapping("/confirm/{token}")
    public ResponseEntity confirmVisit(@PathVariable String token) {
        VisitToken visitToken = visitTokenService.findByToken(token)
                .orElseThrow(() -> new EntityNotFoundException("VisitToken", token));
        visitService.confirmVisit(visitToken);
        return new ResponseEntity("Visit confirmed successfully", HttpStatus.OK);
    }

    @GetMapping("/cancel/{token}")
    public ResponseEntity cancelVisit(@PathVariable String token) {
        VisitToken visitToken = visitTokenService.findByToken(token)
                .orElseThrow(() -> new EntityNotFoundException("VisitToken", token));
        visitService.cancelVisit(visitToken);
        return new ResponseEntity("Visit cancelled successfully", HttpStatus.NO_CONTENT);
    }
}
