package com.example.tuneguessrserver.session.room;

import lombok.Builder;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;
@Data
@Builder
public class Room implements SessionData{
    private String id;
    private GameMode mode;
    @Builder.Default
    private LinkedList<String> players=new LinkedList<>();
    @Builder.Default
    private int maxPlayers=10;
    private String hostId;
    public void addPlayer(String playerId){
        if(players.size()>=maxPlayers){
            throw new RuntimeException("Room is full");
        }
        System.out.println(players);
        System.out.println(players.getClass());
        players.add(playerId);
    }
}
