package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity(name = "PlaceInfo")
@Table(name = "Places")
public class PlaceInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private int id;

    @Column(name = "title")
    @JsonProperty("title")
    private String title;

    @Column(name = "description")
    @JsonProperty("description")
    private String description;

    @Lob
    @Column(name = "image", length=16777215)
    @JsonProperty("image")
    private byte[] image;

    @Column(name = "grade")
    @JsonProperty("grade")
    private double grade;

    @Column(name = "latitude")
    @JsonProperty("latitude")
    private double latitude;

    @Column(name = "longitude")
    @JsonProperty("longitude")
    private double longitude;

    public PlaceInfo() {
    }

    public RouteInfo getRouteInfo() {
        return routeInfo;
    }

    public void setRouteInfo(RouteInfo routeInfo) {
        this.routeInfo = routeInfo;
        routeInfo.getPlaces().add(this);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_info_id", referencedColumnName = "id")
    @JsonIgnore
    private RouteInfo routeInfo;

    public PlaceInfo(String title, String description, byte[] image, double grade, double latitude, double longitude) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.grade = grade;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
