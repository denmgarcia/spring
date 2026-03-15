package com.example.demo.repository;

import com.example.demo.entity.Category;
import com.example.demo.entity.Pokemon;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PokemonRepository extends JpaRepository<Pokemon, Long> {

//    @EntityGraph(attributePaths = {"category"})
//    @Override
//    List<Pokemon> findAll();

    Pokemon findByName(String name);

    List<Pokemon> findByNameContainingIgnoreCase(String keyword);


}
