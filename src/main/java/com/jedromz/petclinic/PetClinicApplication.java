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
import java.math.BigDecimal;
import java.time.LocalDate;
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
                            .ownerEmail("karen.smith@gmail.com")
                            .ownerName("karen")
                            .race("coondell")
                            .type("Dog")
                            .birthDate(LocalDate.now().minusYears(10))
                            .build(),
                    Pet.builder().petName("rocky")
                            .ownerEmail("johndoe@gmail.com")
                            .ownerName("karen")
                            .race("egyptian")
                            .type("cat")
                            .birthDate(LocalDate.now().minusYears(10))
                            .build(),
                    Pet.builder().petName("tomash")
                            .ownerEmail("barnaba-blue@gmail.com")
                            .ownerName("barnaba")
                            .race("ara")
                            .type("parrot")
                            .birthDate(LocalDate.now().minusYears(10))
                            .build(),
                    Pet.builder().petName("bambo")
                            .ownerEmail("alanwalker@gmail.com")
                            .ownerName("alan")
                            .race("Poodle")
                            .type("Dog")
                            .birthDate(LocalDate.now().minusYears(10))
                            .build(),
                    Pet.builder().petName("bob")
                            .ownerEmail("marthagray@gmail.com")
                            .ownerName("martha")
                            .race("goldfish")
                            .type("fish")
                            .birthDate(LocalDate.now().minusYears(10))
                            .build()));
            vetRepository.saveAllAndFlush(List.of(
                    Vet.builder()
                            .firstName("Jackie")
                            .lastName("Smith")
                            .specialization("Cardio")
                            .petSpecialization("Dog")
                            .nip("12345678910")
                            .rate(BigDecimal.valueOf(350))
                            .build(),
                    Vet.builder()
                            .firstName("Ann")
                            .lastName("Green")
                            .specialization("Surgeon")
                            .petSpecialization("Cat")
                            .nip("10987654321")
                            .rate(BigDecimal.valueOf(150))
                            .build()));
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
