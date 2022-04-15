package com.jedromz.petclinic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.jedromz.petclinic.PetClinicApplication;
import com.jedromz.petclinic.model.Pet;
import com.jedromz.petclinic.model.Vet;
import com.jedromz.petclinic.model.Visit;
import com.jedromz.petclinic.model.command.CreateVisitCommand;
import com.jedromz.petclinic.model.command.UpdateVisitCommand;
import com.jedromz.petclinic.service.PetService;
import com.jedromz.petclinic.service.VetService;
import com.jedromz.petclinic.service.VisitService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = PetClinicApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class VisitControllerTest {
    @Autowired
    private MockMvc postman;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private VisitService visitService;
    @Autowired
    private PetService petService;
    @Autowired
    private VetService vetService;

    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @BeforeEach
    void setUp() {
        visitService.deleteAll();
    }

    @AfterEach
    void tearDown() {
        visitService.deleteAll();
    }

    @Test
    void shouldGetSingleVisit() throws Exception {
        //given
        Pet newPet = Pet.builder().petName("TEST_NAME").birthdate(LocalDate.now().minusYears(10)).type("TEST_TYPE").race("TEST_RACE").ownerEmail("test@ownermail.com").build();
        Vet newVet = Vet.builder().firstname("TEST_FIRSTNAME").lastName("TEST_LASTNAME").nip("1111111111").isFired(false).specialization("TEST_SPECIALIZATION").petSpecialization("TEST_PET_SPECIALIZATION").rate(BigDecimal.valueOf(100.99)).build();
        Vet savedVet = vetService.save(newVet);
        Pet savedPet = petService.save(newPet);
        Visit newVisit = new Visit(LocalDateTime.now().plusDays(1), savedVet, savedPet);
        Visit savedVisit = visitService.save(newVisit);
        long visitId = savedVisit.getId();

        //then
        MvcResult mvcResult = postman
                .perform(get("/visits/{id}", visitId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(visitId))
                .andExpect(jsonPath("$.dateTime").value(newVisit.getDateTime().format(FORMATTER)))
                .andExpect(jsonPath("$.confirmed").value(newVisit.isConfirmed()))
                .andReturn();
    }

    @Test
    void shouldNotGetVisitWithBadId() throws Exception {
        long badId = 1L;
        postman.perform(get("/visits/{id}", badId))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse().getErrorMessage();
    }


    @Test
    void shouldGetAllVisits() throws Exception {
        //given
        LocalDateTime dateTime = LocalDateTime.now().plusDays(1);
        dateTime.format(FORMATTER);

        Pet newPet = Pet.builder().petName("TEST_NAME").birthdate(LocalDate.now().minusYears(10)).type("TEST_TYPE").race("TEST_RACE").ownerEmail("test@ownermail.com").build();
        Vet newVet = Vet.builder().firstname("TEST_FIRSTNAME").lastName("TEST_LASTNAME").nip("1111111111").isFired(false).specialization("TEST_SPECIALIZATION").petSpecialization("TEST_PET_SPECIALIZATION").rate(BigDecimal.valueOf(100.99)).build();
        Vet savedVet = vetService.save(newVet);
        Pet savedPet = petService.save(newPet);

        List<Visit> visits = List.of(
                new Visit(dateTime.plusDays(1), savedVet, savedPet),
                new Visit(dateTime.plusDays(2), savedVet, savedPet),
                new Visit(dateTime.plusDays(3), savedVet, savedPet)
        );
        //then
        visitService.saveVisits(visits);
        postman.perform(get("/visits"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[*].dateTime").value(containsInAnyOrder(visits.get(0).getDateTime().format(FORMATTER),
                        visits.get(1).getDateTime().format(FORMATTER),
                        visits.get(2).getDateTime().format(FORMATTER))))
                .andExpect(jsonPath("$.content[*].confirmed").value(containsInAnyOrder(visits.get(0).isConfirmed(),
                        visits.get(1).isConfirmed(),
                        visits.get(2).isConfirmed())));
    }

    @Test
    void shouldGetEmptyList() throws Exception {
        postman.perform(get("/visits"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[*]").doesNotExist());
    }


    @Test
    void shouldAddVisit() throws Exception {
        //given
        LocalDateTime dateTime = LocalDateTime.now().plusDays(1);
        dateTime.format(FORMATTER);
        Pet newPet = Pet.builder().petName("TEST_NAME").birthdate(LocalDate.now().minusYears(10)).type("TEST_TYPE").race("TEST_RACE").ownerEmail("test@ownermail.com").build();
        Vet newVet = Vet.builder().firstname("TEST_FIRSTNAME").lastName("TEST_LASTNAME").nip("1111111111").isFired(false).specialization("TEST_SPECIALIZATION").petSpecialization("TEST_PET_SPECIALIZATION").rate(BigDecimal.valueOf(100.99)).build();
        Pet savedPet = petService.save(newPet);
        Vet savedVet = vetService.save(newVet);
        CreateVisitCommand command = CreateVisitCommand.builder()
                .dateTime(dateTime.minusMinutes(dateTime.getMinute()))
                .petId(savedPet.getId())
                .vetId(savedVet.getId())
                .build();
        String requestJson = objectMapper.writeValueAsString(
                command);
        //when
        String response = postman.perform(post("/visits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        int visitId = JsonPath.read(response, "id");
        //then
        postman.perform(get("/visits/{id}", visitId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(visitId))
                .andExpect(jsonPath("$.dateTime").value(command.getDateTime().format(FORMATTER)));

    }

    @Test
    void shouldNotAddVisit() throws Exception {
        //given
        CreateVisitCommand command = CreateVisitCommand.builder().build();
        String requestJson = objectMapper.writeValueAsString(
                command);
        //then
        postman.perform(post("/visits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldDeleteVisitById() throws Exception {
        //given
        Pet newPet = Pet.builder().petName("TEST_NAME").birthdate(LocalDate.now().minusYears(10)).type("TEST_TYPE").race("TEST_RACE").ownerEmail("test@ownermail.com").build();
        Vet newVet = Vet.builder().firstname("TEST_FIRSTNAME").lastName("TEST_LASTNAME").nip("1111111111").isFired(false).specialization("TEST_SPECIALIZATION").petSpecialization("TEST_PET_SPECIALIZATION").rate(BigDecimal.valueOf(100.99)).build();
        Vet savedVet = vetService.save(newVet);
        Pet savedPet = petService.save(newPet);
        Visit newVisit = new Visit(LocalDateTime.now().plusDays(1), savedVet, savedPet);
        Visit savedVisit = visitService.save(newVisit);
        long visitId = savedVisit.getId();
        //when
        postman.perform(delete("/visits/{id}", visitId))
                .andExpect(status().isNoContent());
        //then
        postman.perform((get("/visits/{id}", visitId)))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse().getContentAsString();

    }

    @Test
    void shouldEditVisit() throws Exception {
        //given
        Pet newPet = Pet.builder().petName("TEST_NAME").birthdate(LocalDate.now().minusYears(10)).type("TEST_TYPE").race("TEST_RACE").ownerEmail("test@ownermail.com").build();
        Vet newVet = Vet.builder().firstname("TEST_FIRSTNAME").lastName("TEST_LASTNAME").nip("1111111111").isFired(false).specialization("TEST_SPECIALIZATION").petSpecialization("TEST_PET_SPECIALIZATION").rate(BigDecimal.valueOf(100.99)).build();
        Vet savedVet = vetService.save(newVet);
        Pet savedPet = petService.save(newPet);
        Visit newVisit = new Visit(LocalDateTime.now().plusDays(1), savedVet, savedPet);
        Visit savedVisit = visitService.save(newVisit);
        long visitId = savedVisit.getId();
        UpdateVisitCommand command = UpdateVisitCommand.builder()
                .dateTime(savedVisit.getDateTime().plusDays(1))
                .build();
        String requestJson = objectMapper.writeValueAsString(command);
        //when
        postman.perform(MockMvcRequestBuilders.put("/visits/{id}", visitId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());
        //then
        postman.perform(get("/visits/{id}", visitId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(visitId))
                .andExpect(jsonPath("$.dateTime").value(command.getDateTime().format(FORMATTER)))
                .andExpect(jsonPath("$.confirmed").value(newVisit.isConfirmed()))
                .andReturn();
    }
}
