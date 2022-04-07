package com.jedromz.petclinic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.jedromz.petclinic.PetClinicApplication;
import com.jedromz.petclinic.model.Vet;
import com.jedromz.petclinic.model.command.CreateVetCommand;
import com.jedromz.petclinic.model.command.UpdateVetCommand;
import com.jedromz.petclinic.service.VetService;
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
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = PetClinicApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class VetControllerTest {
    @Autowired
    private MockMvc postman;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private VetService vetService;

    @BeforeEach
    void setUp() {
        vetService.deleteAll();
    }

    @AfterEach
    void tearDown() {
        vetService.deleteAll();
    }

    @Test
    void shouldGetSingleVet() throws Exception {
        //given
        Vet newVet = Vet.builder()
                .firstName("TEST_FIRSTNAME")
                .lastName("TEST_LASTNAME")
                .nip("1111111111")
                .isFired(false)
                .specialization("cardiologist")
                .petSpecialization("dogs")
                .rate(BigDecimal.valueOf(100.99))
                .build();
        Vet savedVet = vetService.save(newVet);

        //when
        MvcResult mvcResult = postman.perform(get("/vets/{id}", savedVet.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(newVet.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(newVet.getLastName()))
                .andExpect(jsonPath("$.nip").value(newVet.getNip()))
                .andExpect(jsonPath("$.fired").value(newVet.isFired()))
                .andExpect(jsonPath("$.specialization").value(newVet.getSpecialization()))
                .andExpect(jsonPath("$.petSpecialization").value(newVet.getPetSpecialization()))
                .andExpect(jsonPath("$.rate").value(newVet.getRate()))
                .andReturn();
        //then
        Vet resultVet = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Vet.class);
        assertEquals(savedVet.getFirstName(), resultVet.getFirstName());
        assertEquals(savedVet.getLastName(), resultVet.getLastName());
        assertEquals(savedVet.getNip(), resultVet.getNip());
        assertEquals(savedVet.isFired(), resultVet.isFired());
        assertEquals(savedVet.getSpecialization(), resultVet.getSpecialization());
        assertEquals(savedVet.getPetSpecialization(), resultVet.getPetSpecialization());
        assertEquals(savedVet.getRate(), resultVet.getRate());

    }

    @Test
    void shouldNotVetPetWithBadId() throws Exception {
        long badId = 1L;
        postman.perform(get("/vets/{id}", badId))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse().getErrorMessage();
    }

    @Test
    void shouldGetAllVets() throws Exception {

        //given
        BigDecimal DEFAULT_PRECISION = BigDecimal.valueOf(0.000001);
        LocalDate testBirthdate = LocalDate.now().minusYears(10);
        String firstName = "TEST_FIRSTNAME";
        String lastName = "TEST_LASTNAME";
        String specialization = "TEST_SPECIALIZATION";
        String petSpecialization = "TEST_PET_SPECIALIZATION";
        String nip = "1111111111";
        BigDecimal rate = BigDecimal.valueOf(350.99);
        boolean isFired = false;


        vetService.saveVets(List.of(
                new Vet(firstName + "1", lastName, specialization, petSpecialization, rate, nip, isFired),
                new Vet(firstName + "2", lastName, specialization, petSpecialization, rate, nip, isFired),
                new Vet(firstName + "3", lastName, specialization, petSpecialization, rate, nip, isFired)
        ));
        postman.perform(get("/vets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[*].firstName").value(containsInAnyOrder("TEST_FIRSTNAME1",
                        "TEST_FIRSTNAME2",
                        "TEST_FIRSTNAME3")))
                .andExpect(jsonPath("$.content[*].lastName").value(containsInAnyOrder(lastName,
                        lastName,
                        lastName)))
                .andExpect(jsonPath("$.content[*].specialization").value(containsInAnyOrder(specialization,
                        specialization,
                        specialization)))
                .andExpect(jsonPath("$.content[*].petSpecialization").value(containsInAnyOrder(petSpecialization,
                        petSpecialization,
                        petSpecialization)))
                .andExpect(jsonPath("$.content[*].nip").value(containsInAnyOrder(nip,
                        nip,
                        nip)))
                .andExpect(jsonPath("$.content[*].fired").value(containsInAnyOrder(isFired,
                        isFired,
                        isFired)));
    }

    @Test
    void shouldGetEmptyList() throws Exception {
        postman.perform(get("/vets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[*]").doesNotExist());
    }

    @Test
    void shouldAddVet() throws Exception {
        //given
        CreateVetCommand command = CreateVetCommand.builder()
                .firstName("TEST_FIRSTNAME")
                .lastName("TEST_LASTNAME")
                .specialization("TEST_SPECIALIZATION")
                .petSpecialization("TEST_PET_SPECIALIZATION")
                .nip("11111111111")
                .rate(BigDecimal.valueOf(350))
                .isFired(false)
                .build();
        String requestJson = objectMapper.writeValueAsString(
                command);
        //when
        String response = postman.perform(post("/vets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        int savedId = JsonPath.read(response, "id");
        //then
        postman.perform(get("/vets/{id}", savedId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(command.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(command.getLastName()))
                .andExpect(jsonPath("$.nip").value(command.getNip()))
                .andExpect(jsonPath("$.fired").value(command.isFired()))
                .andExpect(jsonPath("$.specialization").value(command.getSpecialization()))
                .andExpect(jsonPath("$.petSpecialization").value(command.getPetSpecialization()))
                .andReturn();

    }

    @Test
    void shouldNotAddVet() throws Exception {
        //given
        CreateVetCommand command = CreateVetCommand.builder().build();
        String requestJson = objectMapper.writeValueAsString(
                command);
        //when
        postman.perform(post("/vets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest());
    }


    @Test
    void shouldDeleteVetById() throws Exception {
        //given
        Vet newVet = Vet.builder()
                .firstName("TEST_FIRSTNAME")
                .lastName("TEST_LASTNAME")
                .nip("1111111111")
                .isFired(false)
                .specialization("cardiologist")
                .petSpecialization("dogs")
                .rate(BigDecimal.valueOf(100.99))
                .build();
        Vet savedVet = vetService.save(newVet);
        //when
        postman.perform(delete("/vets/{id}", savedVet.getId()))
                .andExpect(status().isNoContent());
        //then
        postman.perform((get("/vets/{id}", savedVet.getId())))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldEditVet() throws Exception {
        //given
        Vet newVet = Vet.builder()
                .firstName("TEST_FIRSTNAME")
                .lastName("TEST_LASTNAME")
                .nip("1111111111")
                .isFired(false)
                .specialization("TEST_SPECIALIZATION")
                .petSpecialization("TEST_PET_SPECIALIZATION")
                .rate(BigDecimal.valueOf(100.99))
                .build();
        Vet savedVet = vetService.save(newVet);
        long vetId = savedVet.getId();
        UpdateVetCommand command = UpdateVetCommand.builder()
                .firstName("EDITED_TEST_FIRSTNAME")
                .lastName("EDITED_TEST_LASTNAME")
                .nip("22222222222")
                .isFired(false)
                .specialization("EDITED_SPEC")
                .petSpecialization("EDITED_PET_SPEC")
                .rate(BigDecimal.valueOf(200.99))
                .version(savedVet.getVersion())
                .build();
        String requestJson = objectMapper.writeValueAsString(command);
        //when
        postman.perform(MockMvcRequestBuilders.put("/vets/{id}", vetId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());
        //then
        postman.perform(get("/vets/" + vetId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(command.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(command.getLastName()))
                .andExpect(jsonPath("$.nip").value(command.getNip()))
                .andExpect(jsonPath("$.fired").value(command.isFired()))
                .andExpect(jsonPath("$.specialization").value(command.getSpecialization()))
                .andExpect(jsonPath("$.petSpecialization").value(command.getPetSpecialization()))
                .andReturn();
    }
}
