package com.jedromz.petclinic.service.implementation;

import com.jedromz.petclinic.model.Pet;
import com.jedromz.petclinic.model.command.UpdatePetCommand;
import com.jedromz.petclinic.repository.PetRepository;
import com.jedromz.petclinic.service.PetService;
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
public class PetServiceImpl implements PetService {

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
    public Pet save(Pet pet) {
        return petRepository.saveAndFlush(pet);
    }


    @Transactional
    public void deleteById(Long id) {
        petRepository.findById(id).ifPresent(petRepository::delete);
    }

    @Transactional
    public Pet edit(Pet toEdit, UpdatePetCommand command) {

        toEdit.setPetName(command.getPetName());
        toEdit.setBirthDate(command.getBirthDate());
        toEdit.setOwnerEmail(command.getOwnerEmail());
        toEdit.setOwnerName(command.getOwnerName());
        toEdit.setRace(command.getRace());
        toEdit.setType(command.getType());
        toEdit.setVersion(command.getVersion());

        return petRepository.save(toEdit);
    }

}
