package com.example.tuneguessrserver.user;

import com.example.tuneguessrserver.challenge.ChallengeModel;
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
    private long id;
    private String nickname;
    private String email;
    private List<ChallengeModel> challengelList;
}
