package com.example.demo.controller;


import com.example.demo.adapter.RestAdapter;
import com.example.demo.request.PaymentRequest;
import com.example.demo.response.APIResponse;
import com.google.gson.Gson;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PaymentController {

    @Autowired
    RestAdapter adapter;

    @Autowired
    Gson gson;

    @PostMapping("/payment")
    public ResponseEntity<?> getPayment(@Valid @RequestBody PaymentRequest paymentRequest) {

        String jsonBody = gson.toJson(paymentRequest);

        ResponseEntity<String> httpResponse = adapter.processClientHttp("http://localhost:8088/api/payment", jsonBody, HttpMethod.POST, "");

        APIResponse response = gson.fromJson(httpResponse.getBody(), APIResponse.class);

        return ResponseEntity.ok(response);


    }
}
