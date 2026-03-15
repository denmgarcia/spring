package com.example.demo.controller;


// N+1


import com.example.demo.entity.Category;
import com.example.demo.entity.Pokemon;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.PokemonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/v2")
public class PokemonController {

    @Autowired
    PokemonRepository pokemonRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/pokemon/list")
    public String listPokemon(@RequestParam(value = "keyword", required = false) String keyword, Model model){

        List<Pokemon> pokemonList;

        if (keyword != null && !keyword.isEmpty()) {
            pokemonList = pokemonRepository.findByNameContainingIgnoreCase(keyword);
        } else {
            pokemonList = pokemonRepository.findAll();
        }

        model.addAttribute("pokemonList", pokemonList);
        model.addAttribute("keyword", keyword);
        return "pokemonV2/list";
    }

    @GetMapping("/pokemon/add")
    public String addPokemon(Model model){
        model.addAttribute("pokemon", new Pokemon());
//        model.addAttribute("categories", categoryRepository.findAll());
        return "pokemonV2/create";
    }

    @PostMapping("/pokemon/add")
    public String addPokemon(@ModelAttribute Pokemon pokemon, Model model){
        pokemonRepository.save(pokemon);
        return "redirect:/v2/pokemon/list";
    }

    @GetMapping("/pokemon/delete/{id}")    // although this should @DeleteMapping the FE is searching for GET
    public String deletePokemon(@PathVariable Long id){
        pokemonRepository.deleteById(id);
        return "redirect:/v2/pokemon/list";
    }

    @GetMapping("/pokemon/edit/{id}")
    public String editPokemon(@PathVariable Long id, Model model){
        Optional<Pokemon> optionalPokemon = pokemonRepository.findById(id);

        if (optionalPokemon.isPresent()) {
            model.addAttribute("pokemon", optionalPokemon.get());
            model.addAttribute("categories", categoryRepository.findAll()); // For category dropdown
            return "pokemonV2/edit";
        }

        return "redirect:/v2/pokemon/list";

    }

    @PostMapping("/pokemon/update")
    public String updatePokemon(@ModelAttribute Pokemon pokemon) {
        pokemon.setId(pokemon.getId());

        pokemonRepository.save(pokemon);
        return "redirect:/v2/pokemon/list";
    }
}
