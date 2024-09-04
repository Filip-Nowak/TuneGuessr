package com.example.tuneguessrserver.session;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class SessionTestController {
    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public String send(String message) {
        return "hello";
    }
}
