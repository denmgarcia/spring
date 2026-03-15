package com.example.demo.controller;


import com.example.demo.configuration.DatabaseConfig;
import com.example.demo.entity.PersonEntity;
import com.example.demo.request.PersonRequest;
import com.example.demo.repository.PersonRepository;
import com.example.demo.request.UserInputRequest;
import com.example.demo.response.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class PersonController {

    @Autowired
    DatabaseConfig config;

    @Autowired
    PersonRepository personRepository;

    @GetMapping("details")
    public ResponseEntity<?> getPersonDetails(){
        System.out.println("=======" + config.getDatabaseURL());

        List<PersonEntity> getPersonEntities = personRepository.findAll();

        APIResponse<List<PersonEntity>> response = new APIResponse<>(
                "Successfully fetch data",
                200,
                getPersonEntities
        );

        Map<String, String> data = new HashMap<>();

        return ResponseEntity.ok(response);
    }

    @PostMapping("details")
    public ResponseEntity<?> addPerson(@RequestBody PersonEntity person1){
        
        personRepository.save(person1);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

//    @DeleteMapping("details/{id}")
    @GetMapping("details/{id}")
    public ResponseEntity<?> findPerson(@PathVariable Long id){

        Optional<PersonEntity> findPerson = personRepository.findById(id);

        APIResponse response = new APIResponse<>("Successfully found", 200, findPerson);


        return ResponseEntity.ok(response);
    }

    @PutMapping("details/{id}")
    public ResponseEntity<?> updatePerson(@PathVariable Long id, @RequestBody PersonRequest person) {
        Optional<PersonEntity> optionalPerson = personRepository.findById(id);

        if(optionalPerson.isPresent()) {
            PersonEntity personToUpdate = optionalPerson.get();
            personToUpdate.setSection(person.getSection());
            personToUpdate.setFirstName(person.getFirstName());
            personToUpdate.setLastName(person.getLastName());
            personRepository.save(personToUpdate);
            APIResponse updatePerson = new APIResponse<>("Successfully Changed", 200, null);
            return ResponseEntity.ok(updatePerson);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id is not found!");

    }

    @GetMapping("test")
    public ResponseEntity<?> getOrders() {
        Map<String, String> orders = new HashMap<>();

        orders.put("ballpen", "Ballpen");

        orders.put("pencil", "Pencil");

        orders.put("notebook", "Notebook");



        return ResponseEntity.ok(orders);
    }


}
