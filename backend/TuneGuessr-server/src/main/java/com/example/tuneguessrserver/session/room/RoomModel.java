package com.example.tuneguessrserver.session.room;

import com.example.tuneguessrserver.game.GameMode;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RoomModel {
    private String id;
    private List<PlayerModel> players;
    private String hostId;
    private long challengeId;
    private GameMode gameMode;
}
