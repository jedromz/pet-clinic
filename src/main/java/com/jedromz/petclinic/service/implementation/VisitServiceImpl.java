package com.jedromz.petclinic.service.implementation;

import com.jedromz.petclinic.model.Vet;
import com.jedromz.petclinic.model.Visit;
import com.jedromz.petclinic.model.command.UpdateVetCommand;
import com.jedromz.petclinic.model.command.UpdateVisitCommand;
import com.jedromz.petclinic.repository.VisitRepository;
import com.jedromz.petclinic.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VisitServiceImpl implements VisitService {

    private VisitRepository visitRepository;

    @Transactional(readOnly = true)
    public Page<Visit> findAll(Pageable pageable) {
        return visitRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Visit> findById(Long id) {
        return visitRepository.findById(id);
    }

    @Transactional
    public Visit save(Visit visit) {
        return visitRepository.saveAndFlush(visit);
    }

    @Transactional
    public void deleteById(Long id) {
        visitRepository.findById(id).ifPresent(visitRepository::delete);
    }

    @Transactional
    public Visit edit(Visit toEdit, UpdateVisitCommand command) {

        toEdit.setDateTime(command.getDateTime());
        toEdit.setVersion(command.getVersion());

        return visitRepository.save(toEdit);
    }
}
