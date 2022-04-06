package com.jedromz.petclinic.service.implementation;

import com.jedromz.petclinic.model.Visit;
import com.jedromz.petclinic.model.VisitToken;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface VisitTokenService {

    Optional<VisitToken> findByToken(String token);
}
