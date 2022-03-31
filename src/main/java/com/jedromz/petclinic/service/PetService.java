package com.jedromz.petclinic.service;

import com.jedromz.petclinic.model.Pet;
import com.jedromz.petclinic.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * //W przychodni powinno byc API dla pacjentow
 * // dodanie pacjenta
 * // pobranie danych pacjenta po id
 * // pobranie wszystkich pacjentow (z paginacja)
 */
@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;


    @Transactional(readOnly = true)
    public Page<Pet> findAll(Pageable pageable) {
        return petRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Pet> findById(Long id) {
        return petRepository.findById(id);
    }

    @Transactional
    public Pet save(Pet student) {
        return petRepository.saveAndFlush(student);
    }

}
