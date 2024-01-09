package com.example.tuneguessrserver.model;

import com.example.tuneguessrserver.entity.Challenge;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserModel {
    private String nickname;
    private String email;
    private List<ChallengeModel> challengelList;
}
