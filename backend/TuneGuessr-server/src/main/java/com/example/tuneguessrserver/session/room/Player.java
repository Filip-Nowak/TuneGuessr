package com.example.tuneguessrserver.session.room;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Player implements SessionData{
    private String id;
    private String nickname;
    private boolean ready;
    private String roomId;

}
