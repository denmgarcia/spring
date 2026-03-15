package com.example.demo.controller;


import com.example.demo.entity.PersonEntity;
import com.example.demo.entity.Pokemon;
import com.example.demo.entity.Category;
import com.example.demo.repository.PersonRepository;
import com.example.demo.repository.PokemonRepository;
import com.example.demo.repository.CategoryRepository;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DashboardController.class)
@Import(DashboardControllerTest.MockConfig.class)
class DashboardControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    PokemonRepository pokemonRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @TestConfiguration
    static class MockConfig {

        @Bean
        PersonRepository personRepository() {
            return Mockito.mock(PersonRepository.class);
        }

        @Bean
        PokemonRepository pokemonRepository() {
            return Mockito.mock(PokemonRepository.class);
        }

        @Bean
        CategoryRepository categoryRepository() {
            return Mockito.mock(CategoryRepository.class);
        }
    }

    @Test
    void testDashboardPage() throws Exception {

        PersonEntity person = new PersonEntity();
        person.setFirstName("John");

        when(personRepository.findAll()).thenReturn(List.of(person));

        mockMvc.perform(get("/dashboard"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("persons"));
    }

    @Test
    void testCreatePersonPage() throws Exception {

        mockMvc.perform(get("/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("person/create"))
                .andExpect(model().attributeExists("successMessage"));
    }

    @Test
    void testPokemonPage() throws Exception {

        Category category = new Category();
        category.setCategory("Electric");

        Pokemon pokemon = new Pokemon();
        pokemon.setName("Pikachu");
        pokemon.setCategory(category);

        when(pokemonRepository.findAll()).thenReturn(List.of(pokemon));
        when(categoryRepository.findAll()).thenReturn(List.of(category));

        mockMvc.perform(get("/pokemon"))
                .andExpect(status().isOk())
                .andExpect(view().name("pokemon/list"))
                .andExpect(model().attributeExists("pokemonList"))
                .andExpect(model().attributeExists("categories"));
    }

    @Test
    void testCreatePokemonPage() throws Exception {

        when(categoryRepository.findAll()).thenReturn(List.of(new Category()));

        mockMvc.perform(get("/pokemon/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("pokemon/create"))
                .andExpect(model().attributeExists("pokemon"))
                .andExpect(model().attributeExists("categories"));
    }

}
