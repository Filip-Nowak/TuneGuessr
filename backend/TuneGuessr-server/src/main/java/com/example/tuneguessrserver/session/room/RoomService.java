package com.example.tuneguessrserver.session.room;

import com.example.tuneguessrserver.challenge.ChallengeService;
import com.example.tuneguessrserver.game.GameMode;
import com.example.tuneguessrserver.session.RedisService;
import com.example.tuneguessrserver.session.user.UserSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RedisService redisService;
    private final UserSessionService userSessionService;
    private final ChallengeService challengeService;
    public Room createRoom(String hostId, long challengeId, String modeName) throws RoomException {
        GameMode mode;
        try {
            mode = GameMode.valueOf(modeName);
        } catch (IllegalArgumentException e) {
            throw new RoomException("Invalid game mode");
        }
        Player player = (Player) redisService.find(hostId);
        if (player.getRoomId() != null) {
            throw new RoomException("Player is already in a room");
        }
        if(!challengeService.challengeExists(challengeId)) {
            throw new RoomException("Challenge does not exist");
        }
        String roomId = redisService.generateRoomId();
        LinkedList<String> list = new LinkedList<>();
        list.add(hostId);
        Room room = Room.builder()
                .id(roomId)
                .hostId(hostId)
                .players(list)
                .challengeId(challengeId)
                .mode(mode)
                .build();
        saveRoom(room);
        player.setRoomId(roomId);
        userSessionService.saveUser(player);
        return room;
    }

    public Room getRoom(String roomId) {
        return (Room) redisService.find(roomId);
    }

    private void saveRoom(Room room) {
        redisService.save(room);
    }

    public void joinRoom(String playerId, String roomId) throws RoomException{
        Player player = (Player) redisService.find(playerId);
        if (player.getRoomId() != null) {
            throw new RoomException("Player is already in a room");
        }
        Room room = getRoom(roomId);
        if(room == null) {
            throw new RoomException("Room does not exist");
        }
        if (room.getPlayers().size() >= room.getMaxPlayers()) {
            throw new RoomException("Room is full");
        }
        room.addPlayer(playerId);
        saveRoom(room);
        player.setRoomId(roomId);
        userSessionService.saveUser(player);
    }

    public Room leaveRoom(String id) throws RoomException{
        Player player = (Player) redisService.find(id);
        Room room = getRoom(player.getRoomId());
        if(room == null) {
            throw new RoomException("Player is not in a room");
        }
        room.getPlayers().remove(id);
        player.setRoomId(null);
        userSessionService.saveUser(player);
        if (room.getPlayers().isEmpty()) {
            redisService.delete(room.getId());
            return null;
        }
        if (room.getHostId().equals(id)) {
            room.setHostId(room.getPlayers().getFirst());
        }
        saveRoom(room);
        return room;
    }


    public List<Player> getPlayers(String id) {
        Room room = getRoom(id);
        List<Player> players = new LinkedList<>();
        for (String playerId : room.getPlayers()) {
            players.add((Player) redisService.find(playerId));
        }
        return players;
    }

    public List<PlayerModel> getPlayerModels(String id) {
        Room room = getRoom(id);
        List<PlayerModel> players = new LinkedList<>();
        for (String playerId : room.getPlayers()) {
            Player player = (Player) redisService.find(playerId);
            System.out.println("chuj xd");
            System.out.println(player);
            players.add(PlayerModel.builder()
                    .id(player.getId())
                    .nickname(player.getNickname())
                    .ready(player.isReady())
                    .build());
        }
        return players;
    }

    public void setPlayerReady(String userId, boolean ready) throws RoomException{
        Player player = (Player) redisService.find(userId);
        if(player.getRoomId() == null) {
            throw new RoomException("Player is not in a room");
        }
        player.setReady(ready);
        userSessionService.saveUser(player);
    }

    public Room changeMode(String roomId, String mode) throws RoomException{
        Room room = getRoom(roomId);
        try{room.setMode(GameMode.valueOf(mode));}
        catch (IllegalArgumentException e) {
            throw new RoomException("Invalid game mode");
        }
        saveRoom(room);
        return room;
    }

    public Room changeChallenge(String roomId, String challengeId) throws RoomException{
        Room room = getRoom(roomId);
        if(!challengeService.challengeExists(Long.parseLong(challengeId))) {
            throw new RoomException("Challenge does not exist");
        }
        room.setChallengeId(Long.parseLong(challengeId));
        saveRoom(room);
        return room;
    }
}
