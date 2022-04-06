package com.jedromz.petclinic.service.implementation;

import com.jedromz.petclinic.model.Visit;
import com.jedromz.petclinic.model.VisitToken;
import com.jedromz.petclinic.repository.VisitRepository;
import com.jedromz.petclinic.repository.VisitTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VisitTokenServiceImpl implements VisitTokenService {

    private final VisitTokenRepository visitTokenRepository;
    private final VisitRepository visitRepository;

    public Optional<VisitToken> findByToken(String token) {
        return visitTokenRepository.findByToken(token);
    }
}
