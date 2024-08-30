package com.example.tuneguessrserver.challenge;

import com.example.tuneguessrserver.user.UserProfile;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class Challenge {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne()
    private UserProfile user;
    private String name;
    private String description;
    @OneToMany(mappedBy = "challenge", cascade=CascadeType.ALL)
    private List<Song> songs;
    public void addSong(Song song){
        if(songs==null)
            songs=new ArrayList<>();
        songs.add(song);
    }
}
