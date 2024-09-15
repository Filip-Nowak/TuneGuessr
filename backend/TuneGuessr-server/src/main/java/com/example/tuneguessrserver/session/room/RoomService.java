package com.example.tuneguessrserver.session.room;

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

    public Room createRoom(String hostId) {
        String roomId = redisService.generateRoomId();
        LinkedList<String> list = new LinkedList<>();
        list.add(hostId);
        Room room = Room.builder()
                .id(roomId)
                .hostId(hostId)
                .players(list)
                .build();
        saveRoom(room);
        Player player = (Player) redisService.find(hostId);
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

    public void joinRoom(String playerId, String roomId) {
        Room room = getRoom(roomId);
        if (room.getPlayers().size() >= room.getMaxPlayers()) {
            throw new RuntimeException("Room is full");
        }
        room.addPlayer(playerId);
        saveRoom(room);
        Player player = (Player) redisService.find(playerId);
        player.setRoomId(roomId);
        userSessionService.saveUser(player);
    }

    public Room leaveRoom(String id) {
        Player player = (Player) redisService.find(id);
        System.out.println(player);
        Room room = getRoom(player.getRoomId());
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

    public void setPlayerReady(String userId, boolean ready) {
        Player player = (Player) redisService.find(userId);
        player.setReady(ready);
        userSessionService.saveUser(player);
    }
}
