package com.example.demo.controller;


import com.example.demo.configuration.DatabaseConfig;
import com.example.demo.entity.PersonEntity;
import com.example.demo.repository.PersonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PersonController.class)
@Import(PersonControllerTest.MockConfig.class)
public class PersonControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    DatabaseConfig databaseConfig;

    @Autowired
    ObjectMapper objectMapper;

    @TestConfiguration
    static class MockConfig {

        @Bean
        PersonRepository personRepository() {
            return Mockito.mock(PersonRepository.class);
        }

        @Bean
        DatabaseConfig databaseConfig() {
            return Mockito.mock(DatabaseConfig.class);
        }
    }

    @Test
    void testGetPersonDetails() throws Exception {

        PersonEntity person = new PersonEntity();
        person.setId(1L);
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setSection("A");

        List<PersonEntity> persons = Arrays.asList(person);

        Mockito.when(personRepository.findAll()).thenReturn(persons);

        mockMvc.perform(get("/api/details"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Successfully fetch data"))
                .andExpect(jsonPath("$.data[0].firstName").value("John"));
    }

    @Test
    void testAddPerson() throws Exception {

        PersonEntity person = new PersonEntity();
        person.setFirstName("Jane");
        person.setLastName("Doe");
        person.setSection("B");

        mockMvc.perform(post("/api/details")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isCreated());
    }

    @Test
    void testFindPerson() throws Exception {

        PersonEntity person = new PersonEntity();
        person.setId(1L);
        person.setFirstName("John");

        Mockito.when(personRepository.findById(1L)).thenReturn(Optional.of(person));

        mockMvc.perform(get("/api/details/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Successfully found"));
    }

    @Test
    void testUpdatePerson() throws Exception {

        PersonEntity existing = new PersonEntity();
        existing.setId(1L);
        existing.setFirstName("Old");

        Mockito.when(personRepository.findById(1L)).thenReturn(Optional.of(existing));

        String requestBody = """
        {
            "firstName": "New",
            "lastName": "User",
            "section": "C"
        }
        """;

        mockMvc.perform(put("/api/details/1")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Successfully Changed"));
    }

    @Test
    void testGetOrders() throws Exception {

        mockMvc.perform(get("/api/test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ballpen").value("Ballpen"))
                .andExpect(jsonPath("$.pencil").value("Pencil"))
                .andExpect(jsonPath("$.notebook").value("Notebook"));
    }

}