package com.example.tuneguessrserver.game;

import com.example.tuneguessrserver.response.websocket.MessageModel;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameLog {
    private MessageModel message;
    private boolean privateMessage;

}
