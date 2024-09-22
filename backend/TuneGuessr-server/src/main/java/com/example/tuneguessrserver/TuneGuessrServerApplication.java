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
    public CommandLineRunner runner(AuthenticationService authenticationService, RoleRepository roleRepository, ConfirmationTokenRepository confirmationTokenRepository, UserProfileRepository userProfileRepository, ChallengeService challengeService, UserService userService) {
        return runner -> {
            loadRoles(roleRepository);
//            loaduser(userService,roleRepository);
//            loadChallenge(challengeService,userService);
            loadMiki(authenticationService, confirmationTokenRepository, userProfileRepository, challengeService);
        };
    }

    private void loadMiki(AuthenticationService authenticationService, ConfirmationTokenRepository confirmationTokenRepository, UserProfileRepository userProfileRepository, ChallengeService challengeService) {
        authenticationService.register(RegisterRequest.builder()
                .email("test@test.gmail.com")
                .password("kanapka")
                .nickname("Mikołaj Bala")
                .build(), true);
        ConfirmationToken token = confirmationTokenRepository.findAll().get(0);
        authenticationService.confirmToken(token.getToken());
        ;
        UserProfile userProfile = userProfileRepository.findByNickname("Mikołaj Bala").orElseThrow();
        loadPRap(challengeService, userProfile);
        loadRap(challengeService, userProfile);
        authenticationService.register(RegisterRequest.builder()
                .email("wlodek@gmail.com")
                .nickname("Włodek")
                .password("wlodek1975")
                .build(), true);
        token = confirmationTokenRepository.findAll().get(1);
        authenticationService.confirmToken(token.getToken());
        UserProfile wlodek = userProfileRepository.findByNickname("Włodek").orElseThrow();
        loadDiscoPolo(challengeService, wlodek);
        loadRock(challengeService, userProfile);
        loadLata80(challengeService, wlodek);
    }

    private void loadDiscoPolo(ChallengeService challengeService, UserProfile userProfile) {
        Challenge challenge = Challenge.builder()
                .name("Klasyki disco polo")
                .user(userProfile)
                .description("Zagraj w te hity disco polo")
                .build();
        challengeService.save(challenge);
        for (String song : discoPolo) {
            String[] split = song.split(";");
            challengeService.addSongToChallenge(challenge, AddSongModel.builder()
                    .artist(split[0])
                    .title(split[1])
                    .url(split[2])
                    .build());
        }
    }

    private void loadRock(ChallengeService challengeService, UserProfile userProfile) {
        Challenge challenge = Challenge.builder()
                .name("Klasyki zagranicznego rocka")
                .user(userProfile)
                .description("Zagraj w te hity rocka")
                .build();
        challengeService.save(challenge);
        for (String song : rock) {
            String[] split = song.split(";");
            challengeService.addSongToChallenge(challenge, AddSongModel.builder()
                    .artist(split[0])
                    .title(split[1])
                    .url(split[2])
                    .build());
        }
    }

    private void loadPRap(ChallengeService challengeService, UserProfile userProfile) {
        Challenge challenge = Challenge.builder()
                .name("Klasyki polskiego rapu")
                .user(userProfile)
                .build();
        challengeService.save(challenge);
        for (String song : pRap) {
            String[] split = song.split(";");
            challengeService.addSongToChallenge(challenge, AddSongModel.builder()
                    .artist(split[0])
                    .title(split[1])
                    .url(split[2])
                    .build());
        }
    }

    private void loadRap(ChallengeService challengeService, UserProfile userProfile) {
        Challenge challenge = Challenge.builder()
                .name("Klasyki rapu")
                .user(userProfile)
                .description("Zagraj w te hity rapu")
                .build();
        challengeService.save(challenge);
        for (String song : rap) {
            String[] split = song.split(";");
            challengeService.addSongToChallenge(challenge, AddSongModel.builder()
                    .artist(split[0])
                    .title(split[1])
                    .url(split[2])
                    .build());
        }
    }

    private void loadLata80(ChallengeService challengeService, UserProfile userProfile) {
        Challenge challenge = Challenge.builder()
                .name("Klasyki lat 80")
                .user(userProfile)
                .build();
        challengeService.save(challenge);
        for (String song : lata80) {
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
            "Akcent;Przez twe oczy zielone;https://www.youtube.com/watch?v=cxtnot8lY4U",
            "Boys;Jesteś szalona;https://www.youtube.com/watch?v=c2i4h7Q-8sA",
            "Weekend;Ona tańczy dla mnie;https://www.youtube.com/watch?v=JvxG3zl_WhU",
            "Classic;Jolka Jolka;https://www.youtube.com/watch?v=p0Wzl9wNXq8",
            "After Party;Ona lubi pomarańcze;https://www.youtube.com/watch?v=qAdIPAdzjvY",
            "Piękni i Młodzi;Niewiara;https://www.youtube.com/watch?v=IfPkYmn-FJ4",
            "Defis;Niespotykany kolor;https://www.youtube.com/watch?v=Ktm9wPev8Cs",
            "Akcent;Kochana wierzę w miłość;https://www.youtube.com/watch?v=zv-Un4U1sNI",
            "Mig;Miód malina;https://www.youtube.com/watch?v=vwCWwZetRaI",
            "Bayer Full;Majteczki w kropeczki;https://www.youtube.com/watch?v=Snm1wr5PVd8",
            "Boys;Wolność;https://www.youtube.com/watch?v=jO3DvsPzMww",
            "Czadoman;Ruda tańczy jak szalona;https://www.youtube.com/watch?v=tgw1yEcWpTU",
            "Piękni i Młodzi;Kocham się w Tobie;https://www.youtube.com/watch?v=M6MIDzlnAME",
            "After Party;Tylko ona jedyna;https://www.youtube.com/watch?v=cVLIqMSYNls",
            "Mig;Co ty mi dasz;https://www.youtube.com/watch?v=EDrOriKrV3g",
            "Akcent;Życie to są chwile;https://www.youtube.com/watch?v=9HwaBpcqubI",
            "After Party;Ona jest taka cudowna;https://www.youtube.com/watch?v=cU1C7IzcLnw",
            "Mig;Wymarzona;https://www.youtube.com/watch?v=GzjOboh588Y",
            "Boys;Łobuz;https://www.youtube.com/watch?v=wEev3j30BDE",
            "After Party;Bujaj się;https://www.youtube.com/watch?v=XbRCWS3VumM",
            "After Party;Nie daj życiu się;https://www.youtube.com/watch?v=sLufPIeOVqg",
    };

    private final String[] rock = new String[]{
            "Queen;Bohemian Rhapsody;https://www.youtube.com/watch?v=fJ9rUzIMcZQ",
            "Led Zeppelin;Stairway to Heaven;https://www.youtube.com/watch?v=QkF3oxziUI4",
            "The Rolling Stones;Paint It Black;https://www.youtube.com/watch?v=O4irXQhgMqg",
            "AC/DC;Back In Black;https://www.youtube.com/watch?v=pAgnJDJN4VA",
            "Nirvana;Smells Like Teen Spirit;https://www.youtube.com/watch?v=hTWKbfoikeg",
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
            "Kizo;Disney;https://www.youtube.com/watch?v=_29dLT6OMTU",
            "Bedoes;1998 (mam to we krwi);https://www.youtube.com/watch?v=cpmWu9n7LaM",
            "Sarius;Wiking;https://www.youtube.com/watch?v=E1OjQ_3kh4A",
            "Taco Hemingway;Polskie tango;https://www.youtube.com/watch?v=i84L16VL6c8",
            "Guzior;Płuca zlepione topami;https://www.youtube.com/watch?v=m5e3V5qKPms",
            "Mata;Patointeligencja;https://www.youtube.com/watch?v=wTAibxp37vE",
            "Quebonafide;SZUBIENICAPESTYCYDYBROŃ;https://www.youtube.com/watch?v=ogBHlyBakWk",
            "Malik Montana;Robię yeah;https://www.youtube.com/watch?v=u9sz3_Avmlo",
            "Paluch;Gdybyś kiedyś;https://www.youtube.com/watch?v=qSNV03we7Cg",
            "Taco Hemingway;W piątki leżę w wannie;https://www.youtube.com/watch?v=3ah4t1P9yFA",
            "Kizo;Taxi;https://www.youtube.com/watch?v=gtdjggvaqsg",
            "Bedoes;Kwiat Polskiej Młodzieży;https://www.youtube.com/watch?v=M6mSIg-FDak",
            "Białas;Grill u Gawrona;https://www.youtube.com/watch?v=_7rz2P6jUqc",
            "Taco Hemingway;Deszcz na betonie;https://www.youtube.com/watch?v=PCQs3vSJ6xA",
            "Quebonafide;Madagaskar;https://www.youtube.com/watch?v=YTziiho5QNU",
            "Paluch;Szaman;https://www.youtube.com/watch?v=x7BtclKr5Jg",
            "Paluch;Balans;https://www.youtube.com/watch?v=Gtm5pGpb4HQ",
            "Pezet;Ukryty w mieście krzyk;https://www.youtube.com/watch?v=42zPPvLT9ek",
            "Taco Hemingway;Następna stacja;https://www.youtube.com/watch?v=TZgBIbqtDnQ",
            "Szpaku;Totoro;https://www.youtube.com/watch?v=59y3FSLxiWk",
            "Otsochodzi;Nowy kolor;https://www.youtube.com/watch?v=ZSPdldxe8W0",
            "Oki;Jakie to uczucie;https://www.youtube.com/watch?v=AC5jjDHDg28",
            "OIO;Worki w tłum;https://www.youtube.com/watch?v=kDp-MYkjJ5A",
            "Taco Hemingway;6 zer;https://www.youtube.com/watch?v=TKO8zmF98nI",
            "Taconafide;Tamagotchi;https://www.youtube.com/watch?v=odWxQ5eEnfE",
            "Paluch;Bez strachu;https://www.youtube.com/watch?v=4pu-lKgm-dg",
            "Taco Hemingway;Cafe Belga;https://www.youtube.com/watch?v=Hyb9wX5Aecc",

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
            "Lady Pank;Mniej niż zero;https://www.youtube.com/watch?v=Lc-26SnSmok",
            "Republika;Biała flaga;https://www.youtube.com/watch?v=gHUDbpI8Ulk",
            "Perfect;Autobiografia;https://www.youtube.com/watch?v=1n0MupX-7AM",
            "Kombi;Słodkiego miłego życia;https://www.youtube.com/watch?v=YMsQ5izhIDo",
            "Budka Suflera;Jolka, Jolka pamiętasz;https://www.youtube.com/watch?v=fYKfvvwsoAY",
            "Lombard;Przeżyj to sam;https://www.youtube.com/watch?v=ZeE3wjUbiaA",
            "T.Love;Chłopaki nie płaczą;https://www.youtube.com/watch?v=X0lkrxFuGr8",
            "Anna Jantar;Nic nie może wiecznie trwać;https://www.youtube.com/watch?v=LBmUBMnuU2k",
            "Perfect;Nie płacz Ewka;https://www.youtube.com/watch?v=G_oKsG8KRZI",
            "Czesław Niemen;Dziwny jest ten świat;https://www.youtube.com/watch?v=wTjLZwpmufw",
            "Myslovitz;Długość dźwięku samotności;https://youtube.com/watch?v=qCIyK3ec4kE",
            "Maanam;Cykady na Cykladach;https://www.youtube.com/watch?v=mh8ZqQS1EPM",
            "Kult;Gdy nie ma dzieci;https://www.youtube.com/watch?v=I8NcELiIMHg",
            "Kombi;Black and White;https://www.youtube.com/watch?v=jsy6owPn4Qg",
            "Budka Suflera;Takie tango;https://www.youtube.com/watch?v=wQ8j_ew4Hso",
            "Kult;Baranek;https://www.youtube.com/watch?v=DqBuIaa2-_s",
            "Perfect;Ale wkoło jest wesoło;https://www.youtube.com/watch?v=1_XsuuQNT9E",
            "Lady Pank;Zawsze tam gdzie Ty;https://www.youtube.com/watch?v=fyoCXePXQF0",
            "Kombi;Nasze randez-vous;https://www.youtube.com/watch?v=XVdr4w4xttg",
            "Republika;Telefony;https://www.youtube.com/watch?v=qEXyPSBYbMA",
            "Budka Suflera;Bal wszystkich świętych;https://www.youtube.com/watch?v=kE87oI5rdew",
            "Perfect;Wszystko ma swój czas;https://www.youtube.com/watch?v=kZuYff81-Fc",
            "Republika;Mamona;https://www.youtube.com/watch?v=-R7qqsT6-T8",
            "Obywatel G.C.;Nie pytaj o Polskę;https://www.youtube.com/watch?v=cSjk6phf3iY",
            "Obywatel G.C.;Tak tak to ja;https://www.youtube.com/watch?v=sOrXHaOqLL8",
            "Lady Pank;Kryzysowa narzeczona;https://www.youtube.com/watch?v=SPnEpzUy2V4"
    };


    private void loadRoles(RoleRepository roleRepository) {
        Role role = Role.builder()
                .name("USER")
                .build();
        roleRepository.save(role);
    }
}