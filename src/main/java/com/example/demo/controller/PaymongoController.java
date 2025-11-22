package com.example.demo.controller;


import com.example.demo.request.PaymongoRequest;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymongoController {


    @PostMapping("")
    public ResponseEntity<?> getPayment(@RequestBody PaymongoRequest paymongoRequest){

        System.out.println(paymongoRequest.getId());
        System.out.println(paymongoRequest.getType());
        System.out.println(paymongoRequest.getPayment_method_allowed());
        return ResponseEntity.ok("");
    }
}
