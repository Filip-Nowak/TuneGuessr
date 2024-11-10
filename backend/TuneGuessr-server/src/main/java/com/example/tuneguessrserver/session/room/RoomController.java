package com.example.tuneguessrserver.session.room;

import com.example.tuneguessrserver.challenge.Challenge;
import com.example.tuneguessrserver.challenge.ChallengeService;
import com.example.tuneguessrserver.game.Game;
import com.example.tuneguessrserver.game.GameMode;
import com.example.tuneguessrserver.game.GameService;
import com.example.tuneguessrserver.response.websocket.CreateRoomMessage;
import com.example.tuneguessrserver.response.websocket.MessageModel;
import com.example.tuneguessrserver.session.PlayerSession;
import com.example.tuneguessrserver.utils.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Controller
@RequiredArgsConstructor
@CrossOrigin
public class RoomController {
    private final RoomService roomService;
    private final PlayerSession playerSession;
    private final SimpMessagingTemplate messagingTemplate;
    private final GameService gameService;
    private final ChallengeService challengeService;

    @MessageMapping("/room/create")
    public void createRoom(@Payload CreateRoomMessage createRoomMessage) {
        try {
        Log.info("Creating room with: \n\tchallenge id: " + createRoomMessage.getChallengeId() + "\n\tgame mode: " + createRoomMessage.getGameMode() +" \n\tby user: " + playerSession.getUserId() + " with nickname: " + playerSession.getNickname());
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
            Log.info("Room created with id: " + room.getId());
            messagingTemplate.convertAndSendToUser(playerSession.getUserId(), "/info", message);

        } catch (RoomException e) {
            Log.info("Room creation failed: " + e.getMessage());
            messagingTemplate.convertAndSendToUser(playerSession.getUserId(), "/info", MessageModel.createRoomErrorInfo(e.getMessage()));


        }
    }

    @MessageMapping("/room/session")
    public void roomSession() {
        Room room = roomService.getRoom(playerSession.getRoomId());
        MessageModel message = MessageModel.createRoomInfo(room);
        messagingTemplate.convertAndSendToUser(playerSession.getUserId(), "/room", message);
    }

    @MessageMapping("/room/join")
    public void joinRoom(@Payload String roomId) {
        try {
            Log.info("Joining room with id: " + roomId + " \n\tby user: " + playerSession.getUserId() + " with nickname: " + playerSession.getNickname());
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
            Log.info("Room joined with id: " + roomId);
            messagingTemplate.convertAndSendToUser(playerSession.getUserId(), "/info", joinedRoomMessage);
        } catch (RoomException e) {
            Log.info("Room joining failed: " + e.getMessage());
            messagingTemplate.convertAndSendToUser(playerSession.getUserId(), "/info", MessageModel.createRoomErrorInfo(e.getMessage()));
        }
    }

    @MessageMapping("/room/leave")
    public void leaveRoom() {
        try {
            Log.info("Leaving room with id: " + playerSession.getRoomId() + "\n\tby user: " + playerSession.getUserId() + " with nickname: " + playerSession.getNickname());
            Room room = roomService.leaveRoom(playerSession.getUserId());
            if (room == null) {
                messagingTemplate.convertAndSend("/room/" + playerSession.getRoomId(), MessageModel.createPlayerLeftInfo(Map.of("playerId", playerSession.getUserId(), "hostId", "")));
                playerSession.setRoomId(null);
                Log.info("User with id: " + playerSession.getUserId() + " left the room");
                return;
            }
            MessageModel message = MessageModel.createPlayerLeftInfo(Map.of("playerId", playerSession.getUserId(), "hostId", room.getHostId()));
            playerSession.setRoomId(null);
            Log.info("Room left with id: " + room.getId());
            messagingTemplate.convertAndSend("/room/" + room.getId(), message);
        } catch (RoomException e) {
            Log.info("Room leaving failed: " + e.getMessage());
            messagingTemplate.convertAndSendToUser(playerSession.getUserId(), "/info", MessageModel.createRoomErrorInfo(e.getMessage()));
        }
    }

    @MessageMapping("/room/ready")
    public void setReady(@Payload boolean ready) {
        try {
            Log.info("Setting ready to: " + ready + "\n\tby user: " + playerSession.getUserId() + " with nickname: " + playerSession.getNickname());
            roomService.setPlayerReady(playerSession.getUserId(), ready);
            playerSession.setReady(ready);
            Room room = roomService.getRoom(playerSession.getRoomId());
            MessageModel message = MessageModel.createPlayerReadyInfo(Map.of("playerId", playerSession.getUserId(), "ready", ready));
            Log.info("Ready set to: " + ready);
            messagingTemplate.convertAndSend("/room/" + room.getId(), message);
        } catch (RoomException e) {
            Log.info("Setting ready failed: " + e.getMessage());
            messagingTemplate.convertAndSendToUser(playerSession.getUserId(), "/info", MessageModel.createRoomErrorInfo(e.getMessage()));
        }
    }

    @MessageMapping("/room/start")
    public void startGame() {
        try {
            Log.info("Starting game in room with id: " + playerSession.getRoomId() + "\n\tby user: " + playerSession.getUserId() + " with nickname: " + playerSession.getNickname());
            Room room = roomService.getRoom(playerSession.getRoomId());
            if (!room.getHostId().equals(playerSession.getUserId())) {
                throw new RoomException("Only host can start the game");
            }
            for (PlayerModel player : roomService.getPlayerModels(room.getId())) {
                if (!player.isReady()) {
                    throw new RoomException("Not all players are ready");
                }
            }
            Log.info("Game started in room with id: " + playerSession.getRoomId());
            gameService.loadGame(room.getId());
            messagingTemplate.convertAndSend("/room/" + room.getId(), MessageModel.createGameStartInfo());
            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            scheduler.schedule(() -> {
                boolean allReady = gameService.checkAllReady(room.getId());
                if (!allReady) {
                    messagingTemplate.convertAndSend("/room/" + room.getId(), MessageModel.createGameErrorInfo("Not all players are ready"));
                    gameService.endGame(room.getId());
                }
            }, 5, TimeUnit.SECONDS);
        } catch (RoomException e) {
            Log.info("Game starting failed: " + e.getMessage());
            messagingTemplate.convertAndSendToUser(playerSession.getUserId(), "/info", MessageModel.createGameErrorInfo(e.getMessage()));
        }

    }
    @MessageMapping("/room/change-mode")
    public void changeMode(@Payload String mode) {
        try {
            Log.info("Changing mode to: " + mode + "\n\tby user: " + playerSession.getUserId() + " with nickname: " + playerSession.getNickname());
            Room room = roomService.getRoom(playerSession.getRoomId());
            if(!room.getHostId().equals(playerSession.getUserId())) {
                throw new RoomException("Only host can change the mode");
            }
            room = roomService.changeMode(playerSession.getRoomId(), mode);
            RoomModel roomModel = RoomModel.builder()
                    .id(room.getId())
                    .hostId(room.getHostId())
                    .players(roomService.getPlayerModels(room.getId()))
                    .challengeId(room.getChallengeId())
                    .gameMode(room.getMode())
                    .build();
            MessageModel message = MessageModel.createRoomOptionsChanged(roomModel);
            Log.info("Mode changed to: " + mode);
            messagingTemplate.convertAndSend("/room/" + room.getId(), message);
        } catch (RoomException e) {
            Log.info("Mode changing failed: " + e.getMessage());
            messagingTemplate.convertAndSendToUser(playerSession.getUserId(), "/info", MessageModel.createRoomErrorInfo(e.getMessage()));
        }
    }
    @MessageMapping("/room/change-challenge")
    public void changeChallenge(@Payload String challengeId) {
        try {
            Log.info("Changing challenge to: " + challengeId + "\n\tby user: " + playerSession.getUserId() + " with nickname: " + playerSession.getNickname());
            Room room = roomService.getRoom(playerSession.getRoomId());
            if(!room.getHostId().equals(playerSession.getUserId())) {
                throw new RoomException("Only host can change the challenge");
            }
            room = roomService.changeChallenge(playerSession.getRoomId(), challengeId);
            RoomModel roomModel = RoomModel.builder()
                    .id(room.getId())
                    .hostId(room.getHostId())
                    .players(roomService.getPlayerModels(room.getId()))
                    .challengeId(room.getChallengeId())
                    .gameMode(room.getMode())
                    .build();
            MessageModel message = MessageModel.createRoomOptionsChanged(roomModel);
            Log.info("Challenge changed to: " + challengeId);
            messagingTemplate.convertAndSend("/room/" + room.getId(), message);
        } catch (RoomException e) {
            Log.info("Challenge changing failed: " + e.getMessage());
            messagingTemplate.convertAndSendToUser(playerSession.getUserId(), "/info", MessageModel.createRoomErrorInfo(e.getMessage()));
        }
    }
}
