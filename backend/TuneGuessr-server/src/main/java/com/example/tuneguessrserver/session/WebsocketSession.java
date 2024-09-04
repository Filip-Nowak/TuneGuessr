package com.example.tuneguessrserver.session;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "websocket", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Data
public class WebsocketSession {
    private int value=0;
    public void addValue(int value) {
        this.value += value;
    }

}
