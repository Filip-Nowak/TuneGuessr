package com.example.tuneguessrserver.user;

import com.example.tuneguessrserver.challenge.Challenge;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(unique = true)
    private String nickname;
    @OneToOne(cascade = CascadeType.ALL)
    private User user;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Challenge> challengeList;
    public void addChallenge(Challenge challenge){
        if(challengeList==null)
            challengeList=new ArrayList<>();
        challengeList.add(challenge);
    }
}
