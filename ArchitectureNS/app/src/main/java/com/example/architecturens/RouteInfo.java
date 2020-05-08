package com.example.architecturens;

import java.util.List;

public final class RouteInfo {
    private final String id;
    private final String title;
    private final List<PlaceInfo> places;

    public RouteInfo(String id, String title, List<PlaceInfo> places) {
        this.id = id;
        this.title = title;
        this.places = places;
    }
}
