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

        /*byte[] imageInByte2 = convertImage("C:/Users/Ivana/Desktop/Backend/src/main/java/com/example/demo/images/20.jpg");


        PlaceInfo place2 = new PlaceInfo("ERA Novkabel",
                "Novosadska fabrika kablova ili skraćeno NOVKABEL osnovana je još između dva svetska rata 1928. godine kao ispostava mađarske kompanije “Felten i Gijom” iz Budimpešte. Već tada postavljeni su temelji da Novi Sad danas postane IT centar." +
                        "" +
                        "Fabriku su tokom rata za svoje potrebe koristili okupatori, a odmah nakon oslobođenja proizvodnja u fabrici Novkabel je obnovljena, pa je proizvodnja počela da raste iz godine u godinu, a fabrika je odigrala značajnu ulogu u obnovi razrušene zemlje. 1950-ih godina formirana su tri pogona u fabrici, užarski, metalurški i kablovski.",
                imageInByte2,
                0,
                45.273289,
                19.817760);

        placeInfoRepository.save(place2);

        byte[] imageInByte = convertImage("C:/Users/Ivana/Desktop/Backend/src/main/java/com/example/demo/images/12.jpg");

        PlaceInfo place1 = new PlaceInfo("Zeleznicka stanica",
                "Prvobitna zgrada železničke stanice Novi Sad nalazila se na mestu današnje Limanske pijace i izgrađena je 1882. godine, a sledeće godine, 5. marta 1883, u Novi Sad stiže prvi voz – iz pravca Subotice. Pruga ka Zemunu puštena je u saobraćaj 10. decembra 1883." +
                        "Motorna kola „Ganc” u Novom Sadu 1937. godine." +
                        "Od 1901. za prevoz putnika sa železničke stanice do centra grada korišćen je omnibus (tramvaj sa konjskom zapregom). Električni tramvaj je uveden 1911, a 1958. se u gradu ukida tramvajski saobraćaj i uvodi autobuski prevoz. Zgrada stare železničke stanice je srušena, a danas je preostala jedino nekadašnja pošta, koja i dalje radi.",
                imageInByte,
                0,
                45.265742,
                19.829463);

        placeInfoRepository.save(place1);

        byte[] imageInByte3 = convertImage("C:/Users/Ivana/Desktop/Backend/src/main/java/com/example/demo/images/9.jpg");

        PlaceInfo place3 = new PlaceInfo("Poslovna zgrada",
                "Poslovna zgrada, u kojoj su su nalazila preduzeca Jugopetrol,Kooprodukt, Zanatkom, a jedno vrijeme i Urbanisticki zavod. Jedna je od prvih gradjevina koje su zacrtale pravac novog bulevara, koji u trenutku zavrsetka zgrade jos nije bio zavrsen na toj dionici. Objekat prati konecepcijsku liniju koju Sibin uspostavlja na zgradi Poljoprivrednog fakulteta, koji se gradi u isto vrijeme. Na arhitekturi zgrade se nepogresivo cita uticaj tada vladajuceg internacionalnog stila.",
                imageInByte3,
                0,
                45.265914,
                19.834986);

        placeInfoRepository.save(place3);

        byte[] imageInByte4 = convertImage("C:/Users/Ivana/Desktop/Backend/src/main/java/com/example/demo/images/11.jpg");

        PlaceInfo place4 = new PlaceInfo("Galerija Pavla Beljanskog",
                "Među odredbama Ugovora o poklonu koji je Pavle Beljanski 18. novembra 1957. godine potpisao sa rukovodstvom AP Vojvodine, bila je i izgradnja izložbenog prostora u Novom Sadu. Pavle Beljanski je takođe zahtevao da i sam učestvuje u izboru projekta i mesta gradnje, te da prati njen tok." +
                        "Izgradnja Spomen-zbirke odobrena je rešenjem Komisije za reviziju projekta 5. januara 1959, a zgrada je svečano otvorena za javnost 22. oktobra 1961. godine. Za lokaciju objekta odabran je prostor na tadašnjem Trgu proleterskih brigada (od 1992. Trg galerija), čijim je naknadnim zatvaranjem Spomen-zbirka skrajnuta u odnosu na prvobitni položaj i zamisao.",
                imageInByte4,
                0,
                45.252955,
                19.845754);

        placeInfoRepository.save(place4);

        byte[] imageInByte5 = convertImage("C:/Users/Ivana/Desktop/Backend/src/main/java/com/example/demo/images/8.jpg");

        PlaceInfo place5 = new PlaceInfo("StartIT centar",
                "Građevina koja zaslužuje posebnu našu pažnju jeste grandiozni objekat u ulici Miroslava Antića. Dugi niz godina ovde je bio smešten IT sektor Vojvođanske banke i na neki način objekat je bio zapušten. Projekat obnove je iziskivao značajna finansijska sredstva, tako da nikad nije odrađen. 2016. godine ljudima iz Startit-a je dodeljen ovaj prostor i otpočeo je na neki način preporod." +
                        "Zgrada je građena do 1956. godine. Sam objekat je građen kao moderna, internacionalni stil. Ono što je zanimljivo za arhitekte i građevince, jeste činjenica da čitav blok napolju podupire svega par „mršavih“ stubova.",
                imageInByte5,
                0,
                45.252122,
                19.845906);

        placeInfoRepository.save(place5);

        byte[] imageInByte6 = convertImage("C:/Users/Ivana/Desktop/Backend/src/main/java/com/example/demo/images/10.jpg");

        PlaceInfo place6 = new PlaceInfo("Skupstina grada",
                "Građena je od 1958. do 1960. godine kada je i useljena. Ima 5.334 kvadratnih metara korisne površine na četiri sprata, s tim što jedan deo ima sprat manje. Ovaj objekat je oduvek sedište gradskog parlamenta i figurira kao jedna od dve glavne zgrade gradske vlasti, ali je u prvo vreme bio centar sreza, pa tek naknadno nosi naziv skupština. Predsednik gradskog parlamenta uvek je sedeo u ovoj zgradi, što je i danas slučaj. Za vreme funkcionisanja sreza, na četvrtom spratu je bio vojni odsek. Zgrada ima tri ulaza,više od stotinu kancelarija, pet sala za sastanke, biblioteku, štampariju i restoran. Štamparija je za potrebe Skupštine napravljena  naknadno, kada je zgrada priključena na centralno grejanje. Do tada je u tom prostoru bila garaža za radnike gradske uprave i lokalne funkcionere.",
                imageInByte6,
                0,
                45.253275,
                19.847348);

        placeInfoRepository.save(place6);

        byte[] imageInByte8 = convertImage("C:/Users/Ivana/Desktop/Backend/src/main/java/com/example/demo/images/13.jpg");

        PlaceInfo place8 = new PlaceInfo("Studio M",
                "Studio M je zaštitni znak Radio Novog Sada i jedan od prepoznatljivih simbola grada. Iako izgrađen još šezdesetih godina prošlog veka (otvoren je 4. decembra 1965. godine) i danas je jedini prostor koji u sebi spaja koncertnu dvoranu i studio za snimanje i prenose. Studio M je arhitektonsko remek delo Pavla Žilnika, za koju je dobio Oktobarsku nagradu grada Novog Sada. Čitava građevina godinama služi kao multimedijalno stecište brojnih umetnika iz zemlje i inostranstva." +
                        "" +
                        "Studio M je studijsko-koncertna sala koja je, iako su u njemu gostovali mnogi solisti i sastavi, prevashodno namenjena orkestrima matične radijske kuće.",
                imageInByte8,
                0,
                45.254652,
                19.849181);

        placeInfoRepository.save(place8);

        byte[] imageInByte9 = convertImage("C:/Users/Ivana/Desktop/Backend/src/main/java/com/example/demo/images/14.jpg");

        PlaceInfo place9 = new PlaceInfo("Muzej savremene umjetnosti",
                "Iako osnovan 1966. godine, zbog nepostojanja prostornih resursa Muzej počinje sa izložbenom i izdavačkom delatnošću 1969. godine, uz povremeno korišćenje prostora drugih javnih ustanova u Novom Sadu (Galerija Matice srpske, Radnički univerzitet, Tribina mladih, Dom JNA). Godine 1984. se useljava u iznajmljeni prostor u „Sportskom i poslovnom centru Vojvodina” gde ostaje do 1999. godine. Za ovih 15 godina Muzej prvi put dobija mogućnost da u kontinuitetu razvija svoje osnovne programske, istraživačke i muzeološke aktivnosti što je rezultiralo mnogim značajnim ostvarenjima za savremenu umetnost Vojvodine. Nakon novembra 1999. godine Muzej ponovo ostaje bez izložbenog prostora i realizuje izložbe u drugim ustanovama (Galerija Ogranka SANU, 2000/2001; Muzej Vojvodine - prostor nekadašnjeg Muzeja socijalističke revolucije, od 2001. godine).",
                imageInByte9,
                0,
                45.256650,
                19.853263);

        placeInfoRepository.save(place9);

        byte[] imageInByte7 = convertImage("C:/Users/Ivana/Desktop/Backend/src/main/java/com/example/demo/images/12.jpg");
        RouteInfo routeInfo = new RouteInfo("Modernizam (1954-1969)", "Svi objekti, javne i stambene namene,  bili su pod znatnim uticajem poznosecesijskog koncepta gradnje, ali i raznih istoricističkih neostilova koji su još suvereno vladali arhitektonskim izrazom perifernih evropskih regija. Prava moderna arhitektura u gradu započinje pojavom Lazara Dunđerskog i Đorđa Tabakovića u prvim godinama četvrte decenije XX vijeka, školovanih u Beču, odnosno Budimpešti i Beogradu. U narednih nekoliko godina njima se priključuje i niz, takođe u inostranstvu školovanih, arhitekata, koji su za kratko vrijeme uspjeli da izgrade svoj osobeni arhitektonski izraz modernog senzibiliteta. ",2,4,imageInByte7);
        routeInfoRepository.save(routeInfo);
        routeInfo.getPlaces().add(place2);
        routeInfo.getPlaces().add(place1);
        routeInfo.getPlaces().add(place3);
        routeInfo.getPlaces().add(place4);
        routeInfo.getPlaces().add(place5);
        routeInfo.getPlaces().add(place6);
        routeInfo.getPlaces().add(place8);
        routeInfo.getPlaces().add(place9);

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
