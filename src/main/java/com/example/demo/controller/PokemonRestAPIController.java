package com.example.demo.controller;


import com.example.demo.entity.Pokemon;
import com.example.demo.response.APIResponse;
import com.example.demo.service.PokemonRestAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/pokemon")
public class PokemonRestAPIController {

    @Autowired
    PokemonRestAPIService pokemonRestAPIService;

    @GetMapping("/lists")
    public ResponseEntity<?> getPokemonList() {
        APIResponse api = new APIResponse<>("Test", 200, pokemonRestAPIService.getPokemonList());
        return ResponseEntity.ok(api);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchPokemon(@RequestParam String pokemon) {
        Optional<Pokemon> findPokemon = pokemonRestAPIService.searchPokemon(pokemon);

        if(findPokemon.isEmpty()){
            APIResponse<?> apiResponse = new APIResponse<>("Not found", 404, null);
            return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(findPokemon);
    }

    @GetMapping("/search/{pokemon}")
    public ResponseEntity<?> pokemonPath(@PathVariable String pokemon) {
        return this.searchPokemon(pokemon);
    }

    @GetMapping("/request")
    public ResponseEntity<?> pokemonPath(@RequestBody Pokemon pokemon) {
        System.out.println(pokemon.getName());
        System.out.println(pokemon.getCategory());
        return ResponseEntity.ok("sfsdf");
    }



}
