package com.example.tuneguessrserver.session;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "websocket", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Data
public class PlayerSession {
    private String nickname;
    private String userId;
    private String roomId;
    private boolean ready;
}
