package com.jedromz.petclinic.repository;

import com.jedromz.petclinic.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitRepository extends JpaRepository<Visit, Long> {

}
