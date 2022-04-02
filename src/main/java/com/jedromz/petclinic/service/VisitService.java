package com.jedromz.petclinic.service;

import com.jedromz.petclinic.model.Visit;
import com.jedromz.petclinic.model.command.UpdateVisitCommand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface VisitService {

    Page<Visit> findAll(Pageable pageable);


    Optional<Visit> findById(Long id);


    Visit save(Visit visit);

    void deleteById(Long id);

    Visit edit(Visit toEdit, UpdateVisitCommand command);
}
