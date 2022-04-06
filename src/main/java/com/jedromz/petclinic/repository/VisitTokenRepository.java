package com.jedromz.petclinic.repository;

import com.jedromz.petclinic.model.VisitToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VisitTokenRepository extends JpaRepository<VisitToken, Long> {

    Optional<VisitToken> findByToken(String token);

    void deleteByToken(String token);
}
