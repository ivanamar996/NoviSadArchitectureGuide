package com.example.demo.controllers;

import com.example.demo.model.PlaceInfo;
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

       /* byte[] imageInByte2 = convertImage("C:/Users/Ivana/Desktop/Backend/src/main/java/com/example/demo/images/15.jpg");


        PlaceInfo place2 = new PlaceInfo("Trzni centar Bazar",
                "Stoteks je nekada bila najveća robna kuća u Novom Sadu i Vojvodini, a smatrala se jednom od najlepših trgovačkih kuća u nekadašnjoj Jugoslaviji. Projektovao ju je za trgovačko preduzeće Stoteks ljubljanski arhitekta Milan Mihelič, 1972. godine u modernističkom stilu sa jasnim arhitektonskim obeležjima ideologije tog vremena. U to vreme smatrana je za najbolje slovenačko arhitektonsko delo modernog doba. Robna kuća otvorena je zvanično 1984. godine, a od tada je više puta menjala ime. Sada se zove tržni centar Bazaar.",
                imageInByte2,
                0,
                45.253857,
                19.844088);

        placeInfoRepository.save(place2);

        byte[] imageInByte = convertImage("C:/Users/Ivana/Desktop/Backend/src/main/java/com/example/demo/images/17.jpg");

        PlaceInfo place1 = new PlaceInfo("EPS Distribucija",
                "Specifičnost zgrade Elektrovojvodine je i to što o njoj laička javnost ima prilično pozitivno mišljenje, što nije slučaj s većinom modernističkih objekata iz perioda socijalizma, piše arhitekta Aleksandar Bede." +
                        "Objekat Elektrovojvodine u Novom Sadu je verovatno najmonumentalnija zgrada u gradu o kojoj se najmanje pisalo. Uglavnom se pominje tek usputno u hronikama izgradnje u Vojvodini, ili u opusu arhitekte Milana Matovića, sa šturim podatkom da je izgrađena 1977. godine (negde 1978). Crteži iz njenog idejnog rešenja nisu bili objavljivani, niti su je hroničari ili istoričari arhitekture posebno analizirali.",
                imageInByte,
                0,
                45.245450,
                19.840192);

        placeInfoRepository.save(place1);

        byte[] imageInByte3 = convertImage("C:/Users/Ivana/Desktop/Backend/src/main/java/com/example/demo/images/19.jpg");

        PlaceInfo place3 = new PlaceInfo("SPENS",
                "Nikao je na temeljima manje industrijske zone, jednog od neuglednijih delova grada, a njegovu izgradnju su većinski finansirali građani Novog Sada putem samodoprinosa, državne banke i SIZ-ovi za obrazovanje, zdravstvo, fizičku kulturu. SPENS je izgrađen od naroda za narod, stoga ne iznenađuje činjenica da su Novosađani od samog početka imali osećaj lične povezanosti sa SPENS-om, do te mere da su svoje kućne biljke poklanjali kako bi se ovaj grandiozni betonsko-stakleni prostor ozeleneo. Te biljke i dan danas krase holove SPENS-a.",
                imageInByte3,
                0,
                45.248451,
                19.844361);

        placeInfoRepository.save(place3);

        byte[] imageInByte4 = convertImage("C:/Users/Ivana/Desktop/Backend/src/main/java/com/example/demo/images/7.jpg");

        PlaceInfo place4 = new PlaceInfo("Poljoprivredni fakultet",
                "Te 1954. godine stekli su se društveni i ekonomski uslovi koji su doveli do donošenja Zakona o osnivanju Filozofskog i Poljoprivrednog fakulteta u Novom Sadu u sastavu Univerziteta u Beogradu, sa upisom u prvu godinu studenata u školskoj 1954/55. godini s tim da se materijalna sredstva obezbede za 1954. godinu, iz budžeta Autonomne Pokrajine Vojvodine. Zatim je usvojen od strane Republičkog veća Narodne Skupštine Narodne Republike Srbije na sednici od 26 juna 1954. godine ukaz o proglašenju Zakona o osnivanju Filozofskog i Poljoprivrednog fakulteta u Novom Sadu na osnovu Ustavnog zakona što je proglasio predsednik Izvršnog veća NR Srbije Jovan Veselinov (30. juni 1954. godine).",
                imageInByte4,
                0,
                45.247263,
                19.851965);

        placeInfoRepository.save(place4);

        byte[] imageInByte5 = convertImage("C:/Users/Ivana/Desktop/Backend/src/main/java/com/example/demo/images/18.jpg");

        PlaceInfo place5 = new PlaceInfo("Filozofski fakultete",
                "Zgrada Filozofskog fakulteta je na ovom ciklusu izložbe sigurno bila najatraktivnija pojava. Ovaj objekat je jasno na tragu kritike moderne i u tom smislu interesantna je njegova pozicija u kampusu, koja je pored Poljoprivrednog fakulteta para Đorđević. S druge strane, sve projektantske odluke – upotreba modularnog konstruktivnog sklopa, upotreba materijala u prirodnom obliku i razvijanje prostora objekata – jesu bile odluke zasnovane na realnim potrebama i projektnim zadatkom. Po pitanju ovoga, Aleksandar Stjepanović, autor objekta, u razgovoru sa nama iznosi jednu zanimljivu konstataciju u pogledu vezivanja arhitekture za pravac Brutalizma i saopštava da: „Brutalizam nije posledica filozofije, nego posledica mogućih uslova“. Ovo govori o pametnom odnosu prema projektnom zadatku i podseća da se i u racionalnom pristupu projektovanju, kroz kreativni pristup može proizvesti kvalitetna arhitektura, spektakularnog izgleda.",
                imageInByte5,
                0,
                45.246983,
                19.853325);

        placeInfoRepository.save(place5);


        byte[] imageInByte7 = convertImage("C:/Users/Ivana/Desktop/Backend/src/main/java/com/example/demo/images/17.jpg");
        RouteInfo routeInfo = new RouteInfo(" Postmodernizam (1970-1983)", "Uvećano bogatstvo pojedinaca, kao i unosan veleposjednički posao podizanja najamnih zgrada stvorili su u Novom Sadu uslove za pojavu individualne i kolektivne stambene gradnje koja nije bila limitirana materijalnim sredstvima niti opterećena tradicionalizmom poručioca. U to doba podignute su i neke od najznamenitijih novosadskih građevina koje će ostati arhitektonski simboli Moderne ne samo Novog Sada već i čitave Vojvodine i Srbije. ",3,5,imageInByte7);
        routeInfoRepository.save(routeInfo);
        routeInfo.getPlaces().add(place2);
        routeInfo.getPlaces().add(place1);
        routeInfo.getPlaces().add(place3);
        routeInfo.getPlaces().add(place4);
        routeInfo.getPlaces().add(place5);


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
