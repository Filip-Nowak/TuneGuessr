package com.example.tuneguessrserver;

import com.example.tuneguessrserver.auth.passwordReset.ConfirmationToken;
import com.example.tuneguessrserver.challenge.Challenge;
import com.example.tuneguessrserver.challenge.requests.AddSongModel;
import com.example.tuneguessrserver.auth.passwordReset.ConfirmationTokenRepository;
import com.example.tuneguessrserver.user.RoleRepository;
import com.example.tuneguessrserver.user.*;
import com.example.tuneguessrserver.auth.AuthenticationService;
import com.example.tuneguessrserver.auth.requests.RegisterRequest;
import com.example.tuneguessrserver.challenge.ChallengeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class TuneGuessrServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TuneGuessrServerApplication.class, args);
    }
    @Bean
    public CommandLineRunner runner(AuthenticationService authenticationService, RoleRepository roleRepository,ConfirmationTokenRepository confirmationTokenRepository, UserProfileRepository userProfileRepository, ChallengeService challengeService, UserService userService){
        return runner->{
            loadRoles(roleRepository);
//            loaduser(userService,roleRepository);
//            loadChallenge(challengeService,userService);
            loadMiki(authenticationService,confirmationTokenRepository,userProfileRepository,challengeService);
        };
    }

    private void loadMiki(AuthenticationService authenticationService, ConfirmationTokenRepository confirmationTokenRepository, UserProfileRepository userProfileRepository,ChallengeService challengeService) {
        authenticationService.register(RegisterRequest.builder()
                .email("test@test.gmail.com")
                .password("kanapka")
                .nickname("Mikołaj Bala")
                .build(),true);
        ConfirmationToken token = confirmationTokenRepository.findAll().get(0);
        authenticationService.confirmToken(token.getToken());;
        UserProfile userProfile = userProfileRepository.findByNickname("Mikołaj Bala").orElseThrow();
        loadPRap(challengeService,userProfile);
        loadRap(challengeService,userProfile);
        authenticationService.register(RegisterRequest.builder()
                .email("wlodek@gmail.com")
                .nickname("Włodek")
                .password("wlodek1975")
                .build(),true);
        token = confirmationTokenRepository.findAll().get(1);
        authenticationService.confirmToken(token.getToken());
        UserProfile wlodek = userProfileRepository.findByNickname("Włodek").orElseThrow();
        loadDiscoPolo(challengeService,wlodek);
        loadRock(challengeService,userProfile);
        loadLata80(challengeService,wlodek);
    }

    private void loadDiscoPolo(ChallengeService challengeService,UserProfile userProfile) {
        Challenge challenge=Challenge.builder()
                .name("Klasyki disco polo")
                .user(userProfile)
                .description("Zagraj w te hity disco polo")
                .build();
        challengeService.save(challenge);
        for(String song:discoPolo){
            String[] split = song.split(";");
            challengeService.addSongToChallenge(challenge, AddSongModel.builder()
                    .artist(split[0])
                    .title(split[1])
                    .url(split[2])
                    .build());
        }
    }
    private void loadRock(ChallengeService challengeService,UserProfile userProfile) {
        Challenge challenge=Challenge.builder()
                .name("Klasyki zagranicznego rocka")
                .user(userProfile)
                .description("Zagraj w te hity rocka")
                .build();
        challengeService.save(challenge);
        for(String song:rock){
            String[] split = song.split(";");
            challengeService.addSongToChallenge(challenge, AddSongModel.builder()
                    .artist(split[0])
                    .title(split[1])
                    .url(split[2])
                    .build());
        }
    }
private void loadPRap(ChallengeService challengeService,UserProfile userProfile) {
        Challenge challenge=Challenge.builder()
                .name("Klasyki polskiego rapu")
                .user(userProfile)
                .build();
        challengeService.save(challenge);
        for(String song:pRap){
            String[] split = song.split(";");
            challengeService.addSongToChallenge(challenge, AddSongModel.builder()
                    .artist(split[0])
                    .title(split[1])
                    .url(split[2])
                    .build());
        }
    }
    private void loadRap(ChallengeService challengeService,UserProfile userProfile) {
        Challenge challenge=Challenge.builder()
                .name("Klasyki rapu")
                .user(userProfile)
                .description("Zagraj w te hity rapu")
                .build();
        challengeService.save(challenge);
        for(String song:rap){
            String[] split = song.split(";");
            challengeService.addSongToChallenge(challenge, AddSongModel.builder()
                    .artist(split[0])
                    .title(split[1])
                    .url(split[2])
                    .build());
        }
    }
    private void loadLata80(ChallengeService challengeService,UserProfile userProfile) {
        Challenge challenge=Challenge.builder()
                .name("Klasyki lat 80")
                .user(userProfile)
                .build();
        challengeService.save(challenge);
        for(String song:lata80){
            String[] split = song.split(";");
            challengeService.addSongToChallenge(challenge, AddSongModel.builder()
                    .artist(split[0])
                    .title(split[1])
                    .url(split[2])
                    .build());
        }
    }
    private final String[] discoPolo = new String[]{
            "Akcent;Przekorny los;https://www.youtube.com/watch?v=MWilsN_5Y-s",
            "Akcent;Przez twe oczy zielone;https://www.youtube.com/watch?v=cxtnot8lY4U&pp=ygUGYWtjZW50",
            "Boys;Jesteś szalona;https://www.youtube.com/watch?v=snAyyxpFFvw",
            "Weekend;Ona tańczy dla mnie;https://www.youtube.com/watch?v=DNXwicxlsvI",
            "Classic;Jolka Jolka;https://www.youtube.com/watch?v=sYdVPJkdoJE",
            "After Party;Ona lubi pomarańcze;https://www.youtube.com/watch?v=G9A-ffIzwZY",
            "Piękni i Młodzi;Niewiara;https://www.youtube.com/watch?v=8cbFqB3pA4E",
            "Defis;Niespotykany kolor;https://www.youtube.com/watch?v=WejsPtCKgtQ",
            "Power Play;Zawsze coś (oj tam, oj tam);https://www.youtube.com/watch?v=TDl-ATFQKO0",
            "Akcent;Kochana wierzę w miłość;https://www.youtube.com/watch?v=_SVty_vp7Ek",
            "Mig;Miód malina;https://www.youtube.com/watch?v=GkjYPyVu2kA",
            "Bayer Full;Majteczki w kropeczki;https://www.youtube.com/watch?v=kUs_iyIgtmU",
            "Milano;Jasnowłosa;https://www.youtube.com/watch?v=zV_YpZt8L3o",
            "Top One;Ciao Italia;https://www.youtube.com/watch?v=GhfCW59-E7M",
            "Weekend;Za miłość mą;https://www.youtube.com/watch?v=0IjxLk4nDNE",
            "Boys;Wolność;https://www.youtube.com/watch?v=-bFxNzhJXRA",
            "Czadoman;Ruda tańczy jak szalona;https://www.youtube.com/watch?v=nc5UM67X4m8",
            "Piękni i Młodzi;Kocham się w Tobie;https://www.youtube.com/watch?v=IsWl1pzZ0YA",
            "After Party;Tylko ona jedyna;https://www.youtube.com/watch?v=ndzz8DeNhfE",
            "Focus;Oczy zielone;https://www.youtube.com/watch?v=2DlsDXLptL0",
            "Akcent;Życie to są chwile;https://www.youtube.com/watch?v=HD7gHBGhH9E",
            "Defis;Tak bardzo tego chcę;https://www.youtube.com/watch?v=bR61nKGVHp4",
            "Power Play;Co ma być to będzie;https://www.youtube.com/watch?v=QhFRpmQ-X5I",
            "Piękni i Młodzi;Jak w bajce;https://www.youtube.com/watch?v=Js8YfpGWFb4",
            "After Party;Ona jest taka cudowna;https://www.youtube.com/watch?v=q_RLb5iRh00",
            "Weekend;Za zdrowie Pań;https://www.youtube.com/watch?v=lMOUW8T-wPU",
            "Mig;Wymarzona;https://www.youtube.com/watch?v=OwIoH1laCbY",
            "Boys;Łobuz;https://www.youtube.com/watch?v=K2kQ4bR_hmg",
            "Classic;To nie przyjaźń tylko miłość;https://www.youtube.com/watch?v=j9ExlfQuTko",
            "Akcent;Królowa nocy;https://www.youtube.com/watch?v=wYjxy8pWW_c"
    };

    private final String[] rock = new String[]{
            "Queen;Bohemian Rhapsody;https://www.youtube.com/watch?v=fJ9rUzIMcZQ",
            "Led Zeppelin;Stairway to Heaven;https://www.youtube.com/watch?v=QkF3oxziUI4",
            "The Rolling Stones;Paint It Black;https://www.youtube.com/watch?v=O4irXQhgMqg",
            "AC/DC;Back In Black;https://www.youtube.com/watch?v=pAgnJDJN4VA",
            "Nirvana;Smells Like Teen Spirit;https://www.youtube.com/watch?v=hTWKbfoikeg",
            "Pink Floyd;Comfortably Numb;https://www.youtube.com/watch?v=_FrOQC-zEog",
            "Guns N' Roses;Sweet Child O' Mine;https://www.youtube.com/watch?v=1w7OgIMMRc4",
            "The Beatles;Hey Jude;https://www.youtube.com/watch?v=A_MjCqQoLLA",
            "Metallica;Enter Sandman;https://www.youtube.com/watch?v=CD-E-LDc384",
            "Bon Jovi;Livin' on a Prayer;https://www.youtube.com/watch?v=lDK9QqIzhwk",
            "The Eagles;Hotel California;https://www.youtube.com/watch?v=EqPtz5qN7HM",
            "U2;With or Without You;https://www.youtube.com/watch?v=XmSdTa9kaiQ",
            "The Who;Baba O'Riley;https://www.youtube.com/watch?v=x2KRpRMSu4g",
            "Aerosmith;Dream On;https://www.youtube.com/watch?v=89dGC8de0CA",
            "Deep Purple;Smoke on the Water;https://www.youtube.com/watch?v=zUwEIt9ez7M",
            "The Doors;Riders on the Storm;https://www.youtube.com/watch?v=iv8GW1GaoIc",
            "Lynyrd Skynyrd;Sweet Home Alabama;https://www.youtube.com/watch?v=ye5BuYf8q4o",
            "Bruce Springsteen;Born to Run;https://www.youtube.com/watch?v=IxuThNgl3YA",
            "Jimi Hendrix;Purple Haze;https://www.youtube.com/watch?v=fjwWjx7Cw8I",
            "The Clash;London Calling;https://www.youtube.com/watch?v=EfK-WX2pa8c",
            "KISS;Rock And Roll All Nite;https://www.youtube.com/watch?v=YkwQbuAGLj4",
            "AC/DC;Highway to Hell;https://www.youtube.com/watch?v=l482T0yNkeo",
            "Queen;We Will Rock You;https://www.youtube.com/watch?v=-tJYN-eG1zk",
            "The Rolling Stones;Sympathy For The Devil;https://www.youtube.com/watch?v=GgnClrx8N2k",
            "Led Zeppelin;Whole Lotta Love;https://www.youtube.com/watch?v=HQmmM_qwG4k",
            "Black Sabbath;Paranoid;https://www.youtube.com/watch?v=0qanF-91aJo",
            "Foo Fighters;Everlong;https://www.youtube.com/watch?v=eBG7P-K-r1Y",
            "Pearl Jam;Alive;https://www.youtube.com/watch?v=qM0zINtulhM",
            "Red Hot Chili Peppers;Californication;https://www.youtube.com/watch?v=YlUKcNNmywk",
            "The Police;Every Breath You Take;https://www.youtube.com/watch?v=OMOGaugKpzs"
    };
    private final String[] pRap = new String[]{
            "Kali;Jesteś lekiem na całe zło;https://www.youtube.com/watch?v=SD5ANa0zI8w",
            "Malik Montana;Mordo Weź;https://www.youtube.com/watch?v=pzMrY3j1oZc",
            "Kizo;Disney;https://www.youtube.com/watch?v=uoM4Qv8zA7I",
            "Mr. Polska;Jagodzianki;https://www.youtube.com/watch?v=ISFL1IH3FnM",
            "Sobel;Fiołkowe pole;https://www.youtube.com/watch?v=ZwFqR9LLrVI",
            "Bedoes;1998 (mam to we krwi);https://www.youtube.com/watch?v=hphOjIRqCBQ",
            "Sarius;Wiking;https://www.youtube.com/watch?v=OvX-zW-UM3A",
            "Malik Montana;Midas;https://www.youtube.com/watch?v=yJ6TeA5elGE",
            "Kizo;Nasze życie;https://www.youtube.com/watch?v=ru5bdmQ5UXg",
            "Żabson;Trapollo;https://www.youtube.com/watch?v=dJr9WBXbjUE",
            "Quebonafide;Candy;https://www.youtube.com/watch?v=FqyxEy94DHE",
            "Taco Hemingway;Polskie tango;https://www.youtube.com/watch?v=_Q7WL4j-jtE",
            "Guzior;Płuca zlepione topami;https://www.youtube.com/watch?v=bTkzMLKICoY",
            "Żabson;Moje życie jest jak sen;https://www.youtube.com/watch?v=ypsRsw6GG0Y",
            "Mr. Polska;Złote tarasy;https://www.youtube.com/watch?v=3D4mcxBP-w8",
            "Kizo;Patointeligencja;https://www.youtube.com/watch?v=yjyV2Rx4ZXQ",
            "Malik Montana;Jestem królem;https://www.youtube.com/watch?v=jglgxFSXBlA",
            "Tede;Drin za drinem;https://www.youtube.com/watch?v=OBLoI6Af5nI",
            "Białas;Blizny na twarzy;https://www.youtube.com/watch?v=GGeVJ3eL7Xk",
            "Sarius;Wolność;https://www.youtube.com/watch?v=kl8Ot0GdQTw",
            "Quebonafide;SZUBIENICAPESTYCYDYBROŃ;https://www.youtube.com/watch?v=MgHZ4XQNujY",
            "Bedoes;Brzydkie rzeczy;https://www.youtube.com/watch?v=SvqsvKkA22M",
            "White 2115;California;https://www.youtube.com/watch?v=v3iMwsAT7S4",
            "Malik Montana;Robię yeah;https://www.youtube.com/watch?v=w7AgDkg_X08",
            "Paluch;Gdybyś kiedyś;https://www.youtube.com/watch?v=xJYHcxnBbFE",
            "Taco Hemingway;W piątki leżę w wannie;https://www.youtube.com/watch?v=gh8SyaYF1_4",
            "Żabson;W piątek wieczorem;https://www.youtube.com/watch?v=FZ7OVhyms6Y",
            "Kizo;Orkiestra;https://www.youtube.com/watch?v=6kuyOSqbb8A",
            "Sobel;Każdego dnia;https://www.youtube.com/watch?v=pv6xanEDjao",
            "Białas;Nasze życie;https://www.youtube.com/watch?v=K0zpKWWopgY"
    };

    private final String[] rap = new String[]{
            "Kendrick Lamar;HUMBLE.;https://www.youtube.com/watch?v=tvTRZJ-4EyI",
            "Drake;God's Plan;https://www.youtube.com/watch?v=xpVfcZ0ZcFM",
            "Eminem;Lose Yourself;https://www.youtube.com/watch?v=_Yhyp-_hX2s",
            "Travis Scott;SICKO MODE;https://www.youtube.com/watch?v=6ONRf7h3Mdk",
            "Post Malone;Rockstar;https://www.youtube.com/watch?v=UceaB4D0jpo",
            "J. Cole;Middle Child;https://www.youtube.com/watch?v=WILNIXZr2oc",
            "Lil Nas X;Old Town Road;https://www.youtube.com/watch?v=w2Ov5jzm3j8",
            "Kanye West;Stronger;https://www.youtube.com/watch?v=PsO6ZnUZI0g",
            "Cardi B;Bodak Yellow;https://www.youtube.com/watch?v=PEGccV-NOm8",
            "Future;Mask Off;https://www.youtube.com/watch?v=xvZqHgFz51I",
            "Juice WRLD;Lucid Dreams;https://www.youtube.com/watch?v=mzB1VGEGcSU",
            "21 Savage;A Lot;https://www.youtube.com/watch?v=TflaAl6b6F0",
            "Migos;Bad and Boujee;https://www.youtube.com/watch?v=S-sJp1FfG7Q",
            "Nicki Minaj;Super Bass;https://www.youtube.com/watch?v=4JipHEz53sU",
            "Lil Wayne;Lollipop;https://www.youtube.com/watch?v=2IH8tNQAzSs",
            "Jay-Z;Empire State of Mind;https://www.youtube.com/watch?v=0UjsXo9l6I8",
            "Tyga;Taste;https://www.youtube.com/watch?v=LjxulQ1bEWg",
            "A$AP Rocky;Praise The Lord (Da Shine);https://www.youtube.com/watch?v=Kbj2Zss-5GY",
            "DJ Khaled;I'm The One;https://www.youtube.com/watch?v=weeI1G46q0o",
            "Lil Uzi Vert;XO Tour Llif3;https://www.youtube.com/watch?v=WrsFXgQk5UI",
            "The Notorious B.I.G.;Juicy;https://www.youtube.com/watch?v=_JZom_gVfuw",
            "2Pac;California Love;https://www.youtube.com/watch?v=5wBTdfAkqGU",
            "Kanye West;Gold Digger;https://www.youtube.com/watch?v=6vwNcNOTVzY",
            "Kendrick Lamar;DNA.;https://www.youtube.com/watch?v=NLZRYQMLDW4",
            "Eminem;The Real Slim Shady;https://www.youtube.com/watch?v=eJO5HU_7_1w",
            "Travis Scott;Goosebumps;https://www.youtube.com/watch?v=Dst9gZkq1a8",
            "Post Malone;Congratulations;https://www.youtube.com/watch?v=SC4xMk98Pdc",
            "Lil Pump;Gucci Gang;https://www.youtube.com/watch?v=4LfJnj66HVQ",
            "Wiz Khalifa;See You Again;https://www.youtube.com/watch?v=RgKAFK5djSk",
            "Nas;If I Ruled The World (Imagine That);https://www.youtube.com/watch?v=mlp-IIG9ApU"
    };
    private final String[] lata80 = new String[]{
            "Lady Pank;Mniej niż zero;https://www.youtube.com/watch?v=WT7sYhdW12g",
            "Republika;Biała flaga;https://www.youtube.com/watch?v=kGkw_HcmaCw",
            "Perfect;Autobiografia;https://www.youtube.com/watch?v=a4oLNgXKx2g",
            "Maanam;Kocham cię, kochanie moje;https://www.youtube.com/watch?v=QG5BkhKwd4o",
            "Urszula;Dmuchawce, latawce, wiatr;https://www.youtube.com/watch?v=YJ6flU5zrYM",
            "Kombi;Słodkiego miłego życia;https://www.youtube.com/watch?v=hfY8veF4fA8",
            "Bajm;Józek, nie daruję ci tej nocy;https://www.youtube.com/watch?v=GnXe7pKNcE0",
            "Budka Suflera;Jolka, Jolka pamiętasz;https://www.youtube.com/watch?v=Hsph_eUazG4",
            "Oddział Zamknięty;Andzia i ja;https://www.youtube.com/watch?v=YmcEdcJ9kwg",
            "Lombard;Przeżyj to sam;https://www.youtube.com/watch?v=eRL1N7nZpwY",
            "T.Love;Chłopaki nie płaczą;https://www.youtube.com/watch?v=r-c6WwiqKwk",
            "Kult;Do Ani;https://www.youtube.com/watch?v=rR9fUfmMAvk",
            "Varius Manx;Orła cień;https://www.youtube.com/watch?v=ShHimE5TGH4",
            "Anna Jantar;Nic nie może wiecznie trwać;https://www.youtube.com/watch?v=-a-PLh5pr_I",
            "Perfect;Nie płacz Ewka;https://www.youtube.com/watch?v=ro8UfBRXjII",
            "Czesław Niemen;Dziwny jest ten świat;https://www.youtube.com/watch?v=wq5c6TG9Hq4",
            "Kayah;Fleciki;https://www.youtube.com/watch?v=ioXlK-ccrxg",
            "Edyta Bartosiewicz;Sen;https://www.youtube.com/watch?v=qPdb6k-4Pcc",
            "Hey;Teksański;https://www.youtube.com/watch?v=ojugEko7pa4",
            "Róże Europy;Jedwab;https://www.youtube.com/watch?v=OQat9Bq9zOY",
            "Formacja Nieżywych Schabuff;Lato;https://www.youtube.com/watch?v=9W7P9D7XXvU",
            "Goya;Smak słów;https://www.youtube.com/watch?v=H0vToRxEl1A",
            "Majka Jeżowska;A ja wolę moją mamę;https://www.youtube.com/watch?v=TyO8_Z4pt2c",
            "Golden Life;Oprócz;https://www.youtube.com/watch?v=FZ57J6fHqJY",
            "Varius Manx;Zanim zrozumiesz;https://www.youtube.com/watch?v=9yt6F7OSpiE",
            "Reni Jusis;Zakręcona;https://www.youtube.com/watch?v=Z_43ftA5j-s",
            "K.A.S.A.;Piękniejsza;https://www.youtube.com/watch?v=zh64m4rk6DQ",
            "Myslovitz;Długość dźwięku samotności;https://www.youtube.com/watch?v=gzG32v4LzDg",
            "Maanam;Cykady na Cykladach;https://www.youtube.com/watch?v=KKx8zsv4X3c",
            "Anna Maria Jopek;Ale jestem;https://www.youtube.com/watch?v=JvIbAjAEFV0",
            "Czerwone Gitary;Niebo z moich stron;https://www.youtube.com/watch?v=WWgkJpLDxJM"
    };




    private void loaduser(UserService userService,RoleRepository roleRepository) {
        User user=User.builder()
                .email("user@gmail.com")
                .roles(List.of(roleRepository.findByName("USER")))
                .password("$2a$10$wzSBD27giCRjTQYEFNd6ZOSsP5NEqARabqxiihK7PQVE0U.Xjs9Q6").build();
        UserProfile userProfile=UserProfile.builder()
                .user(user)
                .nickname("jebacz kurew")
                .build();
        userService.saveProfile(userProfile);
    }

    private void loadChallenge(ChallengeService challengeService, UserService userService) {
        UserProfile userProfile=userService.getProfileByNickname("jebacz kurew");
        Challenge challenge=Challenge.builder()
                .user(userProfile)
                .name("xd").build();
        challengeService.save(challenge);
    }

    private void loadRoles(RoleRepository roleRepository) {
        Role role=Role.builder()
                .name("USER")
                .build();
        roleRepository.save(role);
    }
}