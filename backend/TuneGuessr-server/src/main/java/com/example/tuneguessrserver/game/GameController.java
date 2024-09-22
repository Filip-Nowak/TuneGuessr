package com.example.tuneguessrserver.game;

import com.example.tuneguessrserver.response.websocket.MessageModel;
import com.example.tuneguessrserver.session.PlayerSession;
import com.example.tuneguessrserver.utils.Log;
import io.lettuce.core.ScriptOutputType;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;
    private final PlayerSession playerSession;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/game/next")
    public void ready() {
        String roomId = playerSession.getRoomId();
        GameLog log = gameService.nextSong(playerSession.getUserId(),roomId);
        if (log.isPrivateMessage()) {
            messagingTemplate.convertAndSendToUser(playerSession.getUserId(), "/info", (log.getMessage()));
        } else {
            messagingTemplate.convertAndSend("/room/" + roomId, (log.getMessage()));
        }
    }

    @MessageMapping("/game/ready-to-start")
    public void startGame() {
        String roomId = playerSession.getRoomId();
        synchronized (roomId.intern()) {
            gameService.setReady(playerSession.getUserId());
            boolean allReady = gameService.checkAllReady(roomId);
            if (allReady) {
                GameLog log = gameService.startGame(roomId);
                if (log.isPrivateMessage()) {
                    messagingTemplate.convertAndSendToUser(playerSession.getUserId(), "/info", (log.getMessage()));
                } else {
                    messagingTemplate.convertAndSend("/room/" + roomId, (log.getMessage()));
                }
            }
        }
    }
    @MessageMapping("/game/guess")
    public void guess(@Payload GuessModel guessModel){
        String roomId = playerSession.getRoomId();
        GameLog log = gameService.guess(playerSession.getUserId(),roomId,guessModel.getGuess(),guessModel.isTitle(),guessModel.getTime());
        if (log.isPrivateMessage()) {
            messagingTemplate.convertAndSendToUser(playerSession.getUserId(), "/info", (log.getMessage()));
        } else {
            messagingTemplate.convertAndSend("/room/" + roomId, (log.getMessage()));
        }

    }
    @MessageMapping("/game/end")
    public void endGame() {
        String roomId = playerSession.getRoomId();
        GameLog log = gameService.endGame(roomId);
        if (log.isPrivateMessage()) {
            messagingTemplate.convertAndSendToUser(playerSession.getUserId(), "/info", (log.getMessage()));
        } else {
            messagingTemplate.convertAndSend("/room/" + roomId, (log.getMessage()));
        }
    }
}
