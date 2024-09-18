package com.example.tuneguessrserver.session.room;

import com.example.tuneguessrserver.game.GameService;
import com.example.tuneguessrserver.game.classic.ClassicGameTemplate;
import com.example.tuneguessrserver.response.ResponseModel;
import com.example.tuneguessrserver.response.websocket.CreateRoomMessage;
import com.example.tuneguessrserver.response.websocket.MessageModel;
import com.example.tuneguessrserver.session.PlayerSession;
import com.example.tuneguessrserver.session.SessionModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@CrossOrigin
public class RoomController {
    private final RoomService roomService;
    private final PlayerSession playerSession;
    private final SimpMessagingTemplate messagingTemplate;
    private final GameService gameService;

    @MessageMapping("/room/create")
    public void createRoom(@Payload CreateRoomMessage createRoomMessage) {
        System.out.println(createRoomMessage);
        Room room = roomService.createRoom(playerSession.getUserId(), createRoomMessage.getChallengeId(), createRoomMessage.getGameMode());
        playerSession.setRoomId(room.getId());

        List<PlayerModel> players = roomService.getPlayerModels(room.getId());
        RoomModel model = RoomModel.builder()
                .id(room.getId())
                .hostId(room.getHostId())
                .players(players)
                .challengeId(room.getChallengeId())
                .gameMode(room.getMode())
                .build();
        MessageModel message = MessageModel.createRoomCreationInfo(model);
        messagingTemplate.convertAndSendToUser(playerSession.getUserId(), "/info", message);
    }

    @MessageMapping("/room/session")
    public void roomSession() {
        Room room = roomService.getRoom(playerSession.getRoomId());
        MessageModel message = MessageModel.createRoomInfo(room);
        messagingTemplate.convertAndSendToUser(playerSession.getUserId(), "/room", message);
    }

    @MessageMapping("/room/join")
    public void joinRoom(@Payload String roomId) {
        System.out.println(playerSession.getUserId() + " joined room " + roomId);
        roomService.joinRoom(playerSession.getUserId(), roomId);
        Room room = roomService.getRoom(roomId);
        playerSession.setRoomId(roomId);
        PlayerModel playerModel = PlayerModel.builder()
                .id(playerSession.getUserId())
                .nickname(playerSession.getNickname())
                .ready(playerSession.isReady())
                .build();
        MessageModel newPlayerMessage = MessageModel.createNewPlayerJoinedInfo(playerModel);
        messagingTemplate.convertAndSend("/room/" + roomId, newPlayerMessage);
        RoomModel roomModel = RoomModel.builder()
                .id(room.getId())
                .hostId(room.getHostId())
                .players(roomService.getPlayerModels(room.getId()))
                .challengeId(room.getChallengeId())
                .gameMode(room.getMode())
                .build();
        MessageModel joinedRoomMessage = MessageModel.createJoinedRoomInfo(roomModel);
        messagingTemplate.convertAndSendToUser(playerSession.getUserId(), "/info", joinedRoomMessage);
    }

    @MessageMapping("/room/leave")
    public void leaveRoom() {
        System.out.println(playerSession);
        Room room = roomService.leaveRoom(playerSession.getUserId());
        if (room == null) {
            messagingTemplate.convertAndSend("/room/" + playerSession.getRoomId(), MessageModel.createPlayerLeftInfo(Map.of("playerId", playerSession.getUserId(), "hostId", "")));
            playerSession.setRoomId(null);
            return;
        }
        MessageModel message = MessageModel.createPlayerLeftInfo(Map.of("playerId", playerSession.getUserId(), "hostId", room.getHostId()));
        playerSession.setRoomId(null);
        messagingTemplate.convertAndSend("/room/" + room.getId(), message);
    }

    @MessageMapping("/room/ready")
    public void setReady(@Payload boolean ready) {
        System.out.println("Ready"
        );
        playerSession.setReady(ready);
        roomService.setPlayerReady(playerSession.getUserId(), ready);
        Room room = roomService.getRoom(playerSession.getRoomId());
        MessageModel message = MessageModel.createPlayerReadyInfo(Map.of("playerId", playerSession.getUserId(), "ready", ready));
        messagingTemplate.convertAndSend("/room/" + room.getId(), message);
    }

    @MessageMapping("/room/start")
    public void startGame() {
        Room room = roomService.getRoom(playerSession.getRoomId());
        if (room.getHostId().equals(playerSession.getUserId())) {
            gameService.startGame(room.getId());
            System.out.println("Game started");
            Object game = gameService.getGame(room.getId());
            System.out.println(game);
        }
        messagingTemplate.convertAndSend("/room/" + room.getId(), MessageModel.createGameStartInfo());
    }

}
