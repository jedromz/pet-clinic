package com.jedromz.petclinic;

import com.jedromz.petclinic.model.Pet;
import com.jedromz.petclinic.model.Vet;
import com.jedromz.petclinic.model.Visit;
import com.jedromz.petclinic.repository.PetRepository;
import com.jedromz.petclinic.repository.VetRepository;
import com.jedromz.petclinic.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class PetClinicApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetClinicApplication.class, args);
    }

    @Component
    @RequiredArgsConstructor
    @Transactional
    class DataLoader implements ApplicationRunner {

        private final PetRepository petRepository;
        private final VetRepository vetRepository;
        private final VisitRepository visitRepository;

        @Override
        public void run(ApplicationArguments args) throws Exception {

            petRepository.saveAllAndFlush(List.of(
                    Pet.builder().petName("jackie")
                            .ownerEmail("karen@smith.gmail.com").build(),
                    Pet.builder().petName("rocky").build(),
                    Pet.builder().petName("scooby").build(),
                    Pet.builder().petName("cat").build(),
                    Pet.builder().petName("maya").build()));
            vetRepository.saveAllAndFlush(List.of(
                    Vet.builder().firstName("Jackie")
                            .nip("1234")
                            .build(),
                    Vet.builder().firstName("Ann").build()));
            visitRepository.saveAllAndFlush(List.of(
                    Visit.builder()
                            .dateTime(LocalDateTime.now().plusDays(1))
                            .pet(petRepository.getById(1L))
                            .vet(vetRepository.getById(1L))
                            .build(),
                    Visit.builder()
                            .dateTime(LocalDateTime.now().plusDays(1))
                            .pet(petRepository.getById(2L))
                            .vet(vetRepository.getById(1L))
                            .build(),
                    Visit.builder()
                            .dateTime(LocalDateTime.now().plusDays(1))
                            .pet(petRepository.getById(3L))
                            .vet(vetRepository.getById(1L))
                            .build(),
                    Visit.builder()
                            .dateTime(LocalDateTime.now().plusDays(1))
                            .pet(petRepository.getById(1L))
                            .vet(vetRepository.getById(2L))
                            .build(),
                    Visit.builder()
                            .dateTime(LocalDateTime.now().plusDays(1))
                            .pet(petRepository.getById(1L))
                            .vet(vetRepository.getById(2L))
                            .build()));
        }
    }
}
