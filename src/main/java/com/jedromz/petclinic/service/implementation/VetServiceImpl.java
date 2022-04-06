package com.jedromz.petclinic.service.implementation;
//W przychodni powinno byc API dla pacjentow
// - dodanie pacjenta
// - pobranie danych pacjenta po id
// - pobranie wszystkich pacjentow (z paginacja)

import com.jedromz.petclinic.model.Pet;
import com.jedromz.petclinic.model.Vet;
import com.jedromz.petclinic.model.command.UpdatePetCommand;
import com.jedromz.petclinic.model.command.UpdateVetCommand;
import com.jedromz.petclinic.repository.VetRepository;
import com.jedromz.petclinic.service.VetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VetServiceImpl implements VetService {

    private final VetRepository vetRepository;


    @Transactional(readOnly = true)
    public Page<Vet> findAll(Pageable pageable) {
        return vetRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Vet> findById(Long id) {
        return vetRepository.findById(id);
    }

    @Transactional
    public Vet save(Vet vet) {
        return vetRepository.saveAndFlush(vet);
    }

    @Transactional
    public void deleteById(Long id) {
        vetRepository.findById(id).ifPresent(vetRepository::delete);
    }

    @Transactional
    public Vet edit(Vet toEdit, UpdateVetCommand command) {

        toEdit.setFirstName(toEdit.getFirstName());
        toEdit.setLastName(toEdit.getLastName());
        toEdit.setRate(toEdit.getRate());
        toEdit.setNip(command.getNip());
        toEdit.setSpecialization(command.getSpecialization());
        toEdit.setPetSpecialization(command.getPetSpecialization());
        toEdit.setVersion(command.getVersion());

        return vetRepository.save(toEdit);
    }

    @Override
    public Vet fire(Vet toFire) {
        toFire.setFired(true);
        return vetRepository.saveAndFlush(toFire);
    }

    @Override
    public boolean existsByNip(String nip) {
        System.out.println(vetRepository.existsByNip(nip));
        return vetRepository.existsByNip(nip);
    }

    @Override
    public void deleteAll() {
        vetRepository.deleteAll();
    }


}
