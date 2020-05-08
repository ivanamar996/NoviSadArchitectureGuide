package com.example.architecturens;

import java.util.List;

public final class RouteInfo {
    private final String id;
    private final String title;
    private final List<PlaceInfo> places;
    private final int duration;
    private  final String description;
    private final double kilometres;
    private final String pictureFileName;


    public RouteInfo(String id, String title, List<PlaceInfo> places, int duration, String description, double kilometres, String pictureFileName) {
        this.id = id;
        this.title = title;
        this.places = places;
        this.duration = duration;
        this.description = description;
        this.kilometres = kilometres;
        this.pictureFileName = pictureFileName;
    }
}
