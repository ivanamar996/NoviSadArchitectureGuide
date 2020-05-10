package com.example.architecturens;

import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static DataManager singleInstance=null;

    private List<RouteInfo> mRouteInfos=new ArrayList<>();
    //private List<PlaceInfo> mPlaceInfos=new ArrayList<>();

    public static DataManager getInstance() {
        if(singleInstance == null) {
            singleInstance = new DataManager();
            singleInstance.initializeRoutes();
            //singleInstance.initializeExampleNotes();
        }
        return singleInstance;
    }

    public List<RouteInfo> getRoutes() {
        return mRouteInfos;
    }


    public RouteInfo getRoute(String id) {
        for (RouteInfo course : mRouteInfos) {
            if (id.equals(course.getId()))
                return course;
        }
        return null;
    }

   /* public List<PlaceInfo> getPlaces(RouteInfo route) {
        ArrayList<PlaceInfo> places = new ArrayList<>();
        for(PlaceInfo place:mPlaceInfos) {
            if(route.equals(place.getRoute()))
                places.add(place);
        }
        return places;
    }*/
   private DataManager() {
   }

   private void initializeRoutes() {
       mRouteInfos.add(initRoute1());
       mRouteInfos.add(initRoute2());
       mRouteInfos.add(initRoute3());
   }



    private RouteInfo initRoute1() {
        List<PlaceInfo> places = new ArrayList<>();
        places.add(new PlaceInfo("place1","Katedrala","Katedrala je jedna od najbitnijih znamenitosti centra,kao i samog Novog Sada.Katedrala je jedna od najbitnijih znamenitosti centra,kao i samog Novog Sada.Katedrala je jedna od najbitnijih znamenitosti centra,kao i samog Novog Sada.Katedrala je jedna od najbitnijih znamenitosti centra,kao i samog Novog Sada.Katedrala je jedna od najbitnijih znamenitosti centra,kao i samog Novog Sada.Katedrala je jedna od najbitnijih znamenitosti centra,kao i samog Novog Sada.Katedrala je jedna od najbitnijih znamenitosti centra,kao i samog Novog Sada.", "drawable/katedrala.jpg"));
        places.add(new PlaceInfo("place2", "Katolicka Porta", "Porta se nalazi iza katedrale i sadrzi veci broj ugostiteljskih objekata.Porta se nalazi iza katedrale i sadrzi veci broj ugostiteljskih objekata.Porta se nalazi iza katedrale i sadrzi veci broj ugostiteljskih objekata.Porta se nalazi iza katedrale i sadrzi veci broj ugostiteljskih objekata.Porta se nalazi iza katedrale i sadrzi veci broj ugostiteljskih objekata.Porta se nalazi iza katedrale i sadrzi veci broj ugostiteljskih objekata.Porta se nalazi iza katedrale i sadrzi veci broj ugostiteljskih objekata.Porta se nalazi iza katedrale i sadrzi veci broj ugostiteljskih objekata.", "drawable/porta.jpg"));
        places.add(new PlaceInfo("place3", "Zmaj Jovina ulica", "Navedena ulica predstavlja setaliste i nema saobrcaja u njoj. Ljudi rado setaju tom ulicom.Navedena ulica predstavlja setaliste i nema saobrcaja u njoj. Ljudi rado setaju tom ulicom.Navedena ulica predstavlja setaliste i nema saobrcaja u njoj. Ljudi rado setaju tom ulicom.Navedena ulica predstavlja setaliste i nema saobrcaja u njoj. Ljudi rado setaju tom ulicom.Navedena ulica predstavlja setaliste i nema saobrcaja u njoj. Ljudi rado setaju tom ulicom.Navedena ulica predstavlja setaliste i nema saobrcaja u njoj. Ljudi rado setaju tom ulicom.Navedena ulica predstavlja setaliste i nema saobrcaja u njoj. Ljudi rado setaju tom ulicom.", "drawable/zmajjovina.jpg"));
        return new RouteInfo("route1", "Centar", places, 2, "Centar grada je jako lijep i prepun raskosnih gradjevina iz doba austrougarske.Centar grada je jako lijep i prepun raskosnih gradjevina iz doba austrougarske.Centar grada je jako lijep i prepun raskosnih gradjevina iz doba austrougarske.", 10,"drawable/image1.jpg" );
    }

    private RouteInfo initRoute2() {
        List<PlaceInfo> places = new ArrayList<>();
        places.add(new PlaceInfo("place1","Tvrdjava","Katedrala je jedna od najbitnijih znamenitosti centra,kao i samog Novog Sada.Katedrala je jedna od najbitnijih znamenitosti centra,kao i samog Novog Sada.Katedrala je jedna od najbitnijih znamenitosti centra,kao i samog Novog Sada.Katedrala je jedna od najbitnijih znamenitosti centra,kao i samog Novog Sada.Katedrala je jedna od najbitnijih znamenitosti centra,kao i samog Novog Sada.Katedrala je jedna od najbitnijih znamenitosti centra,kao i samog Novog Sada.Katedrala je jedna od najbitnijih znamenitosti centra,kao i samog Novog Sada.Katedrala je jedna od najbitnijih znamenitosti centra,kao i samog Novog Sada.Katedrala je jedna od najbitnijih znamenitosti centra,kao i samog Novog Sada.", ""));
        places.add(new PlaceInfo("place2", "Sat", "Porta se nalazi iza katedrale i sadrzi veci broj ugostiteljskih objekata.Porta se nalazi iza katedrale i sadrzi veci broj ugostiteljskih objekata.Porta se nalazi iza katedrale i sadrzi veci broj ugostiteljskih objekata.Porta se nalazi iza katedrale i sadrzi veci broj ugostiteljskih objekata.Porta se nalazi iza katedrale i sadrzi veci broj ugostiteljskih objekata.Porta se nalazi iza katedrale i sadrzi veci broj ugostiteljskih objekata.Porta se nalazi iza katedrale i sadrzi veci broj ugostiteljskih objekata.Porta se nalazi iza katedrale i sadrzi veci broj ugostiteljskih objekata.Porta se nalazi iza katedrale i sadrzi veci broj ugostiteljskih objekata.Porta se nalazi iza katedrale i sadrzi veci broj ugostiteljskih objekata.", ""));
        places.add(new PlaceInfo("place3", "Kafici", "Navedena ulica predstavlja setaliste i nema saobrcaja u njoj. Ljudi rado setaju tom ulicom.", ""));
        return new RouteInfo("route2", "Petrovaradin", places, 1, "Petrovaradin grada je jako lijep i prepun raskosnih gradjevina iz doba austrougarske.", 10,"drawable/image2.jpg" );
    }

    private RouteInfo initRoute3() {
        List<PlaceInfo> places = new ArrayList<>();
        places.add(new PlaceInfo("place1","Kej","Katedrala je jedna od najbitnijih znamenitosti centra,kao i samog Novog Sada.", ""));
        places.add(new PlaceInfo("place2", "Kafici", "Porta se nalazi iza katedrale i sadrzi veci broj ugostiteljskih objekata.", ""));
        places.add(new PlaceInfo("place3", "Plaza", "Navedena ulica predstavlja setaliste i nema saobrcaja u njoj. Ljudi rado setaju tom ulicom.", ""));
        return new RouteInfo("route3", "Strand", places, 3, "Strand grada je jako lijep i prepun raskosnih gradjevina iz doba austrougarske.", 10,"drawable/image3.jpg" );
    }
}

