package com.jedromz.petclinic.service;
//W przychodni powinno byc API dla pacjentow
// - dodanie pacjenta
// - pobranie danych pacjenta po id
// - pobranie wszystkich pacjentow (z paginacja)

import com.jedromz.petclinic.repository.VetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VetService {

    private final VetRepository vetRepository;


}
