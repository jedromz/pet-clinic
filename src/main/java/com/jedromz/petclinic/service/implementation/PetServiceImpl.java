package com.jedromz.petclinic.service.implementation;

import com.jedromz.petclinic.model.Pet;
import com.jedromz.petclinic.model.Visit;
import com.jedromz.petclinic.model.command.UpdatePetCommand;
import com.jedromz.petclinic.repository.PetRepository;
import com.jedromz.petclinic.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


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
        return petRepository.findPetWithVisits(id);
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
        toEdit.setBirthdate(command.getBirthDate());
        toEdit.setOwnerEmail(command.getOwnerEmail());
        toEdit.setOwnerName(command.getOwnerName());
        toEdit.setRace(command.getRace());
        toEdit.setType(command.getType());
        toEdit.setVersion(command.getVersion());
        return petRepository.save(toEdit);
    }

    @Transactional
    public boolean existsByEmail(String email) {
        return petRepository.existsByOwnerEmail(email);
    }

    @Transactional
    public void savePets(List<Pet> pets) {
        petRepository.saveAllAndFlush(pets);
    }

    @Transactional
    public boolean isAppointed(Pet pet, LocalDateTime dateTime) {
        return pet.getVisits().stream()
                .map(Visit::getDateTime)
                .filter(dt -> dt.toLocalDate().equals(dateTime.toLocalDate()))
                .filter(dt -> dt.getHour() == dateTime.getHour())
                .anyMatch(dt -> dt.getHour() == dateTime.getMinute());

    }

}
