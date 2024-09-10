package com.example.tuneguessrserver.session.room;

import com.example.tuneguessrserver.response.ResponseModel;
import com.example.tuneguessrserver.response.websocket.MessageModel;
import com.example.tuneguessrserver.session.PlayerSession;
import com.example.tuneguessrserver.session.SessionModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@CrossOrigin
public class RoomController {
    private final RoomService roomService;
    private final PlayerSession playerSession;
    private final SimpMessagingTemplate messagingTemplate;
    @MessageMapping("/room/create")
    public void createRoom() {
        Room room=roomService.createRoom(playerSession.getUserId());
        playerSession.setRoomId(room.getId());
        MessageModel message=MessageModel.createRoomCreationInfo(room);
        messagingTemplate.convertAndSendToUser(playerSession.getUserId(),"/info",message);
    }

    @MessageMapping("/room/session")
    public void roomSession() {
        Room room=roomService.getRoom(playerSession.getRoomId());
        MessageModel message=MessageModel.createRoomInfo(room);
        messagingTemplate.convertAndSendToUser(playerSession.getUserId(),"/room",message);
    }
}
