package com.example.demo.controllers;

import com.example.demo.model.AppComment;
import com.example.demo.model.RateApp;
import com.example.demo.repository.AppCommentRepository;
import com.example.demo.repository.PlaceInfoRepository;
import com.example.demo.repository.RateAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class RateAppController {

    @Autowired
    RateAppRepository rateAppRepository;

    @Autowired
    AppCommentRepository appCommentRepository;

    @GetMapping("/rateApp/{grade}")
    public String rateApp(@PathVariable double grade) throws IOException {
        RateApp rateAppFromDb = rateAppRepository.findAll().get(0);
        rateAppFromDb.setRateGrade((rateAppFromDb.getRateGrade() + grade)/2);
        rateAppRepository.save(rateAppFromDb);
        return "OK";
    }

    @GetMapping("/comment/{com}")
    public String rateApp(@PathVariable String com) throws IOException {
        AppComment comment = new AppComment(com);
        appCommentRepository.save(comment);
        return "OK";
    }
}
