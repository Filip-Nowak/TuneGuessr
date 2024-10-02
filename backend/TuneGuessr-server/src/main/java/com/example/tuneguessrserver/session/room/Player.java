package com.example.tuneguessrserver.session.room;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class Player implements SessionData, Serializable {
    private String id;
    private String nickname;
    private boolean ready;
    private String roomId;

}
