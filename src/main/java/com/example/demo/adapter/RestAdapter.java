package com.example.demo.adapter;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestAdapter {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Gson gson;

    public ResponseEntity<String> processClientHttp(String endpoint, String body, HttpMethod httpMethod, String key) {

        ResponseEntity<String> responseEntity;


        try {

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json");
            headers.add("Accept", "application/json");


            responseEntity = restTemplate.exchange(endpoint, httpMethod, new HttpEntity<>(body, headers), String.class);

        } catch (Exception ex) {

            responseEntity = new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);;
        }

        return responseEntity;
    }


}
