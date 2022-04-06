package com.jedromz.petclinic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.jedromz.petclinic.PetClinicApplication;
import com.jedromz.petclinic.model.Pet;
import com.jedromz.petclinic.model.Vet;
import com.jedromz.petclinic.model.command.CreatePetCommand;
import com.jedromz.petclinic.model.command.UpdatePetCommand;
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
//    @Test
//    void shouldNotGetPetWithBadId() throws Exception {
//        long badId = 1L;
//        postman.perform(get("/pets/{id}", badId))
//                .andExpect(status().isNotFound())
//                .andReturn()
//                .getResponse().getErrorMessage();
//    }
//
//    @Test
//    void shouldGetAllPets() throws Exception {
//        //given
//        LocalDate testBirthdate = LocalDate.now().minusYears(10);
//        String testType = "TEST_TYPE";
//        String testRace = "TEST_RACE";
//        String testOwnerName = "TEST_OWNER_NAME";
//        String testOwnerEmail = "test@ownermail.com";
//
//
//        petService.savePets(List.of(
//                new Pet("TEST_NAME1", testType, testRace, testBirthdate, testOwnerName, testOwnerEmail),
//                new Pet("TEST_NAME2", testType, testRace, testBirthdate, testOwnerName, testOwnerEmail),
//                new Pet("TEST_NAME3", testType, testRace, testBirthdate, testOwnerName, testOwnerEmail)
//        ));
//        postman.perform(get("/pets"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content[*].petName").value(containsInAnyOrder("TEST_NAME1",
//                        "TEST_NAME2",
//                        "TEST_NAME3")))
//                .andExpect(jsonPath("$.content[*].type").value(containsInAnyOrder(testType,
//                        testType,
//                        testType)))
//                .andExpect(jsonPath("$.content[*].race").value(containsInAnyOrder(testRace,
//                        testRace,
//                        testRace)))
//                .andExpect(jsonPath("$.content[*].birthDate").value(containsInAnyOrder(testBirthdate.toString(),
//                        testBirthdate.toString(),
//                        testBirthdate.toString())))
//                .andExpect(jsonPath("$.content[*].ownerName").value(containsInAnyOrder(testOwnerName,
//                        testOwnerName,
//                        testOwnerName)))
//                .andExpect(jsonPath("$.content[*].ownerEmail").value(containsInAnyOrder(testOwnerEmail,
//                        testOwnerEmail,
//                        testOwnerEmail)));
//    }
//
//    @Test
//    void shouldGetEmptyList() throws Exception {
//        postman.perform(get("/pets"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content[*]").doesNotExist());
//    }
//
//    @Test
//    void shouldAddPet() throws Exception {
//        //given
//        CreatePetCommand command = CreatePetCommand.builder()
//                .petName("TEST_NAME")
//                .race("TEST_RACE")
//                .type("TEST_TYPE")
//                .birthDate(LocalDate.now().minusYears(10))
//                .ownerName("TEST_OWNER_NAME")
//                .ownerEmail("test@ownermail.com")
//                .build();
//        String requestJson = objectMapper.writeValueAsString(
//                command);
//        //when
//        String response = postman.perform(post("/pets")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestJson))
//                .andExpect(status().isCreated())
//                .andReturn().getResponse().getContentAsString();
//        int savedId = JsonPath.read(response, "id");
//        //then
//        postman.perform(get("/pets/" + savedId))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(savedId))
//                .andExpect(jsonPath("$.petName").value(command.getPetName()))
//                .andExpect(jsonPath("$.type").value(command.getType()))
//                .andExpect(jsonPath("$.race").value(command.getRace()))
//                .andExpect(jsonPath("$.birthDate").value(command.getBirthDate().toString()))
//                .andExpect(jsonPath("$.ownerName").value(command.getOwnerName()))
//                .andExpect(jsonPath("$.ownerEmail").value(command.getOwnerEmail()));
//
//    }
//
//    @Test
//    void shouldNotAddPet() throws Exception {
//        //given
//        CreatePetCommand command = CreatePetCommand.builder().build();
//        String requestJson = objectMapper.writeValueAsString(
//                command);
//        //when
//        postman.perform(post("/pets")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestJson))
//                .andExpect(status().isBadRequest());
//    }
//
//
//    @Test
//    void shouldDeletePetById() throws Exception {
//        //given
//        Pet newPet = Pet.builder()
//                .petName("TEST_NAME")
//                .birthDate(LocalDate.now().minusYears(10))
//                .type("TEST_TYPE")
//                .race("TEST_RACE")
//                .ownerEmail("test@ownermail.com")
//                .build();
//        Pet savedPet = petService.save(newPet);
//        long petId = newPet.getId();
//        //when
//        postman.perform(delete("/pets/{id}", petId))
//                .andExpect(status().isNoContent());
//        //then
//        String responseString = postman.perform((get("/pets/{id}", petId)))
//                .andExpect(status().isNotFound())
//                .andReturn()
//                .getResponse().getContentAsString();
//        assertEquals("{\"entityName\":\"Pet\",\"entityKey\":\"6\"}", responseString);
//    }
//
//    @Test
//    void shouldEditPet() throws Exception {
//        //given
//        Pet newPet = Pet.builder()
//                .petName("TEST_NAME")
//                .birthDate(LocalDate.now().minusYears(10))
//                .type("TEST_TYPE")
//                .race("TEST_RACE")
//                .ownerEmail("test@ownermail.com")
//                .build();
//        Pet savedPet = petService.save(newPet);
//        long petId = newPet.getId();
//        UpdatePetCommand command = UpdatePetCommand.builder()
//                .petName("EDITED_NAME")
//                .race("EDITED_RACE")
//                .type("EDITED_TYPE")
//                .birthDate(LocalDate.now().minusYears(10))
//                .ownerName("EDITED_OWNER_NAME")
//                .ownerEmail("edited@ownermail.com")
//                .version(savedPet.getVersion())
//                .build();
//        String requestJson = objectMapper.writeValueAsString(command);
//        //when
//        postman.perform(MockMvcRequestBuilders.put("/pets/{id}", petId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestJson))
//                .andExpect(status().isOk());
//        //then
//        postman.perform(get("/pets/" + petId))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(petId))
//                .andExpect(jsonPath("$.petName").value(command.getPetName()))
//                .andExpect(jsonPath("$.type").value(command.getType()))
//                .andExpect(jsonPath("$.race").value(command.getRace()))
//                .andExpect(jsonPath("$.birthDate").value(command.getBirthDate().toString()))
//                .andExpect(jsonPath("$.ownerName").value(command.getOwnerName()))
//                .andExpect(jsonPath("$.ownerEmail").value(command.getOwnerEmail()));
//    }
}
