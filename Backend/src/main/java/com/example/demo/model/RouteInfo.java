package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "RouteInfo")
@Table(name = "Routes")
@JsonTypeName("routes")
public class RouteInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private int id;

    @Column(name = "title")
    @JsonProperty("title")
    private String title;

    @Column(name = "description", length = 1000)
    @JsonProperty("description")
    private String description;

    @Column(name = "duration")
    @JsonProperty("duration")
    private double duration;

    @Column(name = "kilometres")
    @JsonProperty("kilometres")
    private double kilometres;

    @Lob
    @Column(name = "image", length=16777215)
    @JsonProperty("image")
    private byte[] image;


    @OneToMany(
            mappedBy = "routeInfo",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY
    )
    @JsonProperty("places")
    private Set<PlaceInfo> places;

    public RouteInfo(String title, String description, double duration, double kilometres, byte[] image) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.kilometres = kilometres;
        this.image = image;
        this.places = new HashSet<PlaceInfo>();
    }

    public RouteInfo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public double getKilometres() {
        return kilometres;
    }

    public void setKilometres(double kilometres) {
        this.kilometres = kilometres;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Set<PlaceInfo> getPlaces() {
        return places;
    }

    public void setPlaces(Set<PlaceInfo> places) {
        this.places = places;
    }
}
