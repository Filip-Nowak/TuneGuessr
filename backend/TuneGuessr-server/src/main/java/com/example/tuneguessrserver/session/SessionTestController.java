package com.example.tuneguessrserver.session;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class SessionTestController {
    private final WebsocketSession websocketSession;
    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public String send(Map<String, Object> message) {
        int value = (int) message.get("value");
        websocketSession.addValue(value);
        return "Value: " + websocketSession.getValue();

    }
}
