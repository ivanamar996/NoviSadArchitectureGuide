package com.example.demo.controllers;

import com.example.demo.model.RouteInfo;
import com.example.demo.repository.PlaceInfoRepository;
import com.example.demo.repository.RouteInfoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RouteInfoController {

    @Autowired
    RouteInfoRepository routeInfoRepository;

    @Autowired
    PlaceInfoRepository placeInfoRepository;

    @GetMapping("/getAllRoutes")
    public ResponseEntity<String> getAllRoutes() throws IOException {

        /*byte[] imageInByte2 = convertImage("C:/Users/Ivana/Desktop/Backend/src/main/java/com/example/demo/images/4.jpg");

        PlaceInfo place2 = new PlaceInfo("Klajnova palata",
                "Objekat: Krajnova palata \n Arhitekta: Djordje Tabakovic \n Adresa: Kralja Aleksandra 6 \n Godina izgradnje/otvaranja:  1932 \n Tip objekta: stambeni i poslovni",
                imageInByte2,
                0,
                45.254445,
                19.844068);

        placeInfoRepository.save(place2);

        byte[] imageInByte = convertImage("C:/Users/Ivana/Desktop/Backend/src/main/java/com/example/demo/images/6.jpg");

        PlaceInfo place1 = new PlaceInfo("Tanurdziceva palata",
                "Objekat: Tanurdziceva palata \n Arhitekta: Djordje Tabakovic \n Adresa: Modene 1 \n Godina izgradnje/otvaranja:  1934-1936 \n Tip objekta: stambeni i poslovni",
                imageInByte,
                0,
                45.255183,
                19.846376);

        placeInfoRepository.save(place1);

        byte[] imageInByte3 = convertImage("C:/Users/Ivana/Desktop/Backend/src/main/java/com/example/demo/images/5.jpg");

        PlaceInfo place3 = new PlaceInfo("Sokolski dom",
                "Objekat: Sokolski dom \n Arhitekta: Djordje Tabakovic, Dusan Tosic \n Adresa: Ignjata Pavlasa \n Godina izgradnje/otvaranja:  1934-1936 \n Tip objekta: sportski i kulturni",
                imageInByte3,
                0,
                45.255252,
                19.848583);

        placeInfoRepository.save(place3);

        byte[] imageInByte4 = convertImage("C:/Users/Ivana/Desktop/Backend/src/main/java/com/example/demo/images/2.jpg");

        PlaceInfo place4 = new PlaceInfo("Dom novosadske trgovacke omladine",
                "Objekat: Dom novosadske trgovacke omladine \n Arhitekta: Djordje Tabakovic \n Adresa: Bulevar Mihajla Pupina 7 \n Godina izgradnje/otvaranja:  1929-1931 \n Tip objekta: stambeni i poslovni",
                imageInByte4,
                0,
                45.254200,
                19.848498);

        placeInfoRepository.save(place4);

        byte[] imageInByte5 = convertImage("C:/Users/Ivana/Desktop/Backend/src/main/java/com/example/demo/images/1.jpg");

        PlaceInfo place5 = new PlaceInfo("Banska palata - Banovina",
                "Objekat: Banska palata \n Prethodni naziv: Pokrajinska vlada Vojvodine \n Arhitekta: Dragisa Brasovan \n Adresa: Bulevar Mihajla Pupina 16 \n Godina izgradnje/otvaranja:  1930 \n Tip objekta: upravni",
                imageInByte5,
                0,
                45.253538,
                19.849256);

        placeInfoRepository.save(place5);

        byte[] imageInByte6 = convertImage("C:/Users/Ivana/Desktop/Backend/src/main/java/com/example/demo/images/3.jpg");

        PlaceInfo place6 = new PlaceInfo("Radnicki dom",
                "Objekat: Radnicki dom \n Prethodni naziv: Zgrada saveznih samostalnih sindikata \n Arhitekta: Dragisa Brasovan\n Adresa: Bulevar Mihajla Pupina 24 \n Godina izgradnje/otvaranja: 1931 \n Tip objekta: poslovni",
                imageInByte6,
                0,
                45.254016,
                19.852939);

        placeInfoRepository.save(place6);

        byte[] imageInByte7 = convertImage("C:/Users/Ivana/Desktop/Backend/src/main/java/com/example/demo/images/1.jpg");

        RouteInfo routeInfo = new RouteInfo("Medjuratni modenrnizam", "Grad Novi Sad je svoj procvat doživeo krajem treće i početkom četvrte decenije XX veka pretvaranjem u kulturno i društveno središte nove Dunavske banovine (1929) postavši tako jedan od manjih centara nove državne zajednice, pored Beograda, Zagreba, Ljubljane i Sarajeva.",1.5,2,imageInByte7);

        routeInfoRepository.save(routeInfo);
        routeInfo.getPlaces().add(place2);
        routeInfo.getPlaces().add(place1);
        routeInfo.getPlaces().add(place3);
        routeInfo.getPlaces().add(place4);
        routeInfo.getPlaces().add(place5);
        routeInfo.getPlaces().add(place6);

        routeInfoRepository.save(routeInfo);*/

        List<RouteInfo> routes = new ArrayList<RouteInfo>();
        routes = routeInfoRepository.findAll();

        String json = "";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            //json = objectMapper.writer().withRootName("routes").writeValueAsString(routes);
            json = objectMapper.writer().writeValueAsString(routes);
            System.out.println(json);
        } catch(Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<String>(json, HttpStatus.OK);
    }

    private byte[] convertImage(String path) throws IOException {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(path));
        } catch (IOException e) {
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write( img, "jpg", baos );
        baos.flush();
        byte[] imageInByte = baos.toByteArray();
        baos.close();

        return imageInByte;
    }
}
