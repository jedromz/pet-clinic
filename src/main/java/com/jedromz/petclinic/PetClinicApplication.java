package com.jedromz.petclinic;

import com.jedromz.petclinic.model.Pet;
import com.jedromz.petclinic.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@SpringBootApplication
public class PetClinicApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetClinicApplication.class, args);
    }
    @Component
    @RequiredArgsConstructor
    class DataLoader implements ApplicationRunner {

        private final PetRepository petRepository;
        @Override
        public void run(ApplicationArguments args) throws Exception {

            petRepository.saveAllAndFlush(List.of(
                    Pet.builder().petName("jackie").build(),
                    Pet.builder().petName("rocky").build(),
                    Pet.builder().petName("scooby").build(),
                    Pet.builder().petName("cat").build(),
                    Pet.builder().petName("maya").build()
                    ));
        }
    }
}
