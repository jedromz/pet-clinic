package com.jedromz.petclinic.service;

import com.jedromz.petclinic.model.VisitToken;

import java.util.Optional;

public interface VisitTokenService {

    Optional<VisitToken> findByToken(String token);
}
