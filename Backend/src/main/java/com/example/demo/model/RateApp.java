package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.*;

@Entity(name = "RateApp")
@Table(name = "RateApp")
public class RateApp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "rateGrade")
    private double rateGrade;

    public RateApp(int id, double rateGrade) {
        this.id = id;
        this.rateGrade = rateGrade;
    }

    public RateApp() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getRateGrade() {
        return rateGrade;
    }

    public void setRateGrade(double rateGrade) {
        this.rateGrade = rateGrade;
    }
}
