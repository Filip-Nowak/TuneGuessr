package com.example.tuneguessrserver.session.room;

import com.example.tuneguessrserver.session.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RedisService redisService;
    public Room createRoom(Player host) {
        String roomId = redisService.generateRoomId();
        Room room = Room.builder()
                .id(roomId)
                .hostId(host.getId())
                .players(List.of(Player.builder()
                        .id(host.getId())
                        .nickname(host.getNickname())
                        .roomId(roomId)
                        .build()))
                .build();
        saveRoom(room);
        return room;
    }

    public Room getRoom(String roomId) {
        return (Room) redisService.find(roomId);
    }

    private void saveRoom(Room room) {
        for (Player player : room.getPlayers()) {
            redisService.save(player);
        }
        redisService.save(room);
    }

    public void joinRoom(Player player, String roomId) {
        Room room = getRoom(roomId);
        if(room.getPlayers().size() >= room.getMaxPlayers()) {
            throw new RuntimeException("Room is full");
        }
        room.addPlayer(player);
        saveRoom(room);
    }
    public void leaveRoom(Player player) {
        Room room = getRoom(player.getRoomId());
        room.getPlayers().remove(player);
        player.setRoomId(null);
        player.setReady(false);
        redisService.save(player);
        if(room.getPlayers().isEmpty()) {
            redisService.delete(room.getId());
        } else {
            saveRoom(room);
        }
    }

}
