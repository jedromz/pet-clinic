package com.jedromz.petclinic.service.implementation;

import com.jedromz.petclinic.model.Vet;
import com.jedromz.petclinic.model.Visit;
import com.jedromz.petclinic.model.command.UpdateVetCommand;
import com.jedromz.petclinic.repository.VetRepository;
import com.jedromz.petclinic.service.VetService;
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
public class VetServiceImpl implements VetService {

    private final VetRepository vetRepository;


    @Transactional(readOnly = true)
    public Page<Vet> findAll(Pageable pageable) {
        return vetRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Vet> findById(Long id) {
        return vetRepository.findVetWithVisits(id);
    }

    @Transactional
    public Vet save(Vet vet) {
        return vetRepository.saveAndFlush(vet);
    }

    @Transactional
    public void deleteById(Long id) {
        vetRepository.findVetWithVisits(id).ifPresent(vetRepository::delete);
    }

    @Transactional
    public Vet edit(Vet toEdit, UpdateVetCommand command) {

        toEdit.setFirstname(command.getFirstName());
        toEdit.setLastname(command.getLastName());
        toEdit.setRate(command.getRate());
        toEdit.setNip(command.getNip());
        toEdit.setSpecialization(command.getSpecialization());
        toEdit.setPetSpecialization(command.getPetSpecialization());
        toEdit.setVersion(command.getVersion());

        return vetRepository.save(toEdit);
    }

    @Transactional
    public Vet fire(Vet toFire) {
        toFire.setFired(true);
        return vetRepository.saveAndFlush(toFire);
    }

    @Transactional
    public boolean existsByNip(String nip) {
        System.out.println(vetRepository.existsByNip(nip));
        return vetRepository.existsByNip(nip);
    }

    @Transactional
    public void saveVets(List<Vet> vets) {
        vetRepository.saveAllAndFlush(vets);
    }

    @Transactional
    public void deleteAll() {
        vetRepository.deleteAll();
    }

    public boolean isAppointed(Vet vet, LocalDateTime dateTime) {
        return vet.getVisits().stream()
                .map(Visit::getDateTime)
                .filter(dt -> dt.toLocalDate().equals(dateTime.toLocalDate()))
                .filter(dt -> dt.getHour() == dateTime.getHour())
                .anyMatch(dt -> dt.getHour() == dateTime.getMinute());
    }
}
