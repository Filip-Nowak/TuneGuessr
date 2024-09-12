package com.example.tuneguessrserver.session.room;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RoomModel {
    private String id;
    private List<PlayerModel> players;
    private String hostId;
}
