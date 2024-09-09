package com.example.tuneguessrserver.session;

import jakarta.annotation.PostConstruct;
import lombok.Builder;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Scope(value = "websocket", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Data
public class PlayerSession implements Serializable {
    private String nickname;
    private String userId;
    private String roomId;
    private boolean ready;
}
