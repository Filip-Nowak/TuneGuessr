package com.example.tuneguessrserver.session.room;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;
    private final SimpMessagingTemplate messagingTemplate;
    @MessageMapping("/room/create")
    public void createRoom(String request) {
        System.out.println("Creating room");
        messagingTemplate.convertAndSend("/room", "creating");
    }
}
