package com.example.tuneguessrserver.game;

import com.example.tuneguessrserver.response.websocket.MessageModel;
import com.example.tuneguessrserver.session.PlayerSession;
import io.lettuce.core.ScriptOutputType;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;
    private final PlayerSession playerSession;
    private final SimpMessagingTemplate messagingTemplate;
    @MessageMapping("/game/ready")
    public void ready() {
        System.out.println("Ready");
        String roomId = playerSession.getRoomId();
        gameService.setReady(playerSession.getUserId());
        boolean allReady = gameService.checkAllReady(roomId);
        if (allReady) {
            System.out.println("All ready");
            String songUrl = gameService.getCurrentSong(roomId);
            messagingTemplate.convertAndSend("/room/"+roomId, MessageModel.createNextSongInfo(songUrl));
        }

    }
}
