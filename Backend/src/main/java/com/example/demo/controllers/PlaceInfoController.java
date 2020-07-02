package com.example.demo.controllers;

import com.example.demo.model.PlaceInfo;
import com.example.demo.repository.PlaceInfoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
public class PlaceInfoController {

    @Autowired
    PlaceInfoRepository placeInfoRepository;

    @GetMapping("/postGrade/{id}/{grade}")
    public String postGrade(@PathVariable int id, @PathVariable double grade) throws IOException {
        PlaceInfo placeFromDB = placeInfoRepository.findById(id).get();
        placeFromDB.setGrade(grade);
        placeInfoRepository.save(placeFromDB);
        return "OK";
    }

    @GetMapping("/getRecommendedPlaces")
    public ResponseEntity<String> getRecommendedPlaces() throws IOException {
        List<PlaceInfo> places = placeInfoRepository.findAll();

        Collections.sort(places);

        String json = "";
        List<PlaceInfo> recommeded = places.subList(0,6);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            //json = objectMapper.writer().withRootName("routes").writeValueAsString(routes);
            json = objectMapper.writer().writeValueAsString(recommeded);
            System.out.println(json);
        } catch(Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<String>(json, HttpStatus.OK);

    }
}
