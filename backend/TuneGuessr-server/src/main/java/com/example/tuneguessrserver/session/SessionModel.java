package com.example.tuneguessrserver.session;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SessionModel {
    private String nickname;
    private String userId;
    private String roomId;
    private boolean ready;
}
