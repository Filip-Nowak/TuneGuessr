package com.example.tuneguessrserver.session.room;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlayerModel {
    private String id;
    private String nickname;
    private boolean ready;
}
