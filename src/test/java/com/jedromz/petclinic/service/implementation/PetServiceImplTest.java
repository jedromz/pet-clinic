package com.jedromz.petclinic.service.implementation;

import com.jedromz.petclinic.error.EntityNotFoundException;
import com.jedromz.petclinic.model.Pet;
import com.jedromz.petclinic.model.command.UpdatePetCommand;
import com.jedromz.petclinic.repository.PetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PetServiceImplTest {

    private PetServiceImpl petService;
    @Mock
    private PetRepository petRepository;
    private List<Pet> pets;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Pet PET_1 = Pet.builder().build();
        Pet PET_2 = Pet.builder().build();
        Pet PET_3 = Pet.builder().build();
        pets = new ArrayList<>(List.of(PET_1, PET_2, PET_3));
        petService = new PetServiceImpl(petRepository);
    }

    @Test
    void findAll() {
        Page<Pet> expected = new PageImpl<>(pets);
        Mockito.when(petRepository.findAll(Mockito.any(Pageable.class))).thenReturn(expected);
        Page<Pet> result = petService.findAll(Pageable.unpaged());
        Mockito.verify(petRepository).findAll(Mockito.any(Pageable.class));
        assertEquals(expected, result);
    }

    @Test
    void findById() {
        Pet expected = pets.get(0);
        Mockito.when(petRepository.findPetWithVisits(Mockito.anyLong()))
                .thenReturn(Optional.ofNullable(expected));
        Pet result = petService.findById(Mockito.anyLong())
                .orElseThrow(() -> new EntityNotFoundException("Pet", Long.toString(Mockito.anyLong())));
        Mockito.verify(petRepository).findPetWithVisits(Mockito.anyLong());
        assertEquals(expected, result);
    }

    @Test
    void save() {
        Pet expected = pets.get(0);
        Mockito.when(petRepository.saveAndFlush(expected))
                .thenReturn(expected);
        Pet result = petService.save(expected);
        Mockito.verify(petRepository).saveAndFlush(expected);
        assertEquals(expected, result);
    }

    @Test
    void deleteById() {
        petService.deleteById(Mockito.anyLong());
        Mockito.verify(petRepository).findById(Mockito.anyLong());
    }

    @Test
    void edit() {
        Pet expected = pets.get(0);
        Mockito.when(petRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.ofNullable(expected));
        Mockito.when(petRepository.saveAndFlush(expected))
                .thenReturn(expected);
        Pet result = petService.edit(Mockito.any(Pet.class), Mockito.any(UpdatePetCommand.class));
        Mockito.verify(petRepository).findById(Mockito.anyLong());
        Mockito.verify(petRepository).saveAndFlush(expected);
        assertEquals(expected, result);
    }

    @Test
    void existsByEmail() {
        petService.existsByEmail(Mockito.anyString());
        Mockito.verify(petRepository).existsByOwnerEmail(Mockito.anyString());
    }

    @Test
    void savePets() {
        List<Pet> expected = pets;
        Mockito.when(petRepository.saveAllAndFlush(expected))
                .thenReturn(expected);
        petService.savePets(expected);
        Mockito.verify(petRepository).saveAllAndFlush(expected);
    }
}

