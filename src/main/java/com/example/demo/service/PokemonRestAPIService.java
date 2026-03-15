package com.example.demo.service;


import com.example.demo.entity.Pokemon;
import com.example.demo.repository.PokemonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PokemonRestAPIService {

    @Autowired
    PokemonRepository pokemonRepository;

    public List<Pokemon> getPokemonList() {
        return pokemonRepository.findAll();
    }

    public Optional<Pokemon> searchPokemon(String pokemon) {
        Optional<Pokemon> findPokemon = Optional.ofNullable(pokemonRepository.findByName(pokemon));

        return findPokemon;

    }


}
