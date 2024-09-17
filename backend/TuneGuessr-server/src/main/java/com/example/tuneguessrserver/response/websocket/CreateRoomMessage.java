package com.example.tuneguessrserver.response.websocket;

import com.example.tuneguessrserver.game.GameMode;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateRoomMessage {
    private long challengeId;
    private String gameMode;
}
