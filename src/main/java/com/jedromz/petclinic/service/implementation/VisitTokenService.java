package com.jedromz.petclinic.service.implementation;

import com.jedromz.petclinic.model.VisitToken;

import java.util.Optional;

public interface VisitTokenService {

    Optional<VisitToken> findByToken(String token);
}
