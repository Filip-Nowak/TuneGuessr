package com.example.tuneguessrserver.session.user;

import com.example.tuneguessrserver.response.ResponseModel;
import com.example.tuneguessrserver.response.websocket.MessageInfo;
import com.example.tuneguessrserver.response.websocket.MessageModel;
import com.example.tuneguessrserver.session.PlayerSession;
import com.example.tuneguessrserver.session.SessionModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@CrossOrigin
public class UserSessionController {
    private final PlayerSession playerSession;
    private final SimpMessagingTemplate messagingTemplate;
    private final UserSessionService userSessionService;
    @MessageMapping("/user/set-session")
    public void setId(Map<String,String> data) {
        if(data.containsKey("userId")) {
            playerSession.setUserId(data.get("userId"));
        }
        if(data.containsKey("roomId")) {
            playerSession.setRoomId(data.get("roomId"));
        }
        if(data.containsKey("nickname")) {
            playerSession.setNickname(data.get("nickname"));
        }
        if(data.containsKey("ready")) {
            playerSession.setReady(Boolean.parseBoolean(data.get("ready")));
        }
        if(playerSession.getUserId()!=null) {
MessageModel model = MessageModel.createUserInfo(new SessionModel(playerSession));
            messagingTemplate.convertAndSendToUser(playerSession.getUserId(),"/info",model);
        }
    }
    @GetMapping("/create-user")
    public ResponseEntity<ResponseModel> createUser() {
        String id=userSessionService.createUser();
        return ResponseEntity.ok(ResponseModel.builder().data(Map.of(
                "userId",id
        )).build());
    }

    @MessageMapping("/user/session")
    public void session() {
        if(playerSession.getUserId()==null) {
            return;
        }
        MessageModel message = MessageModel.createUserInfo(new SessionModel(playerSession));
        messagingTemplate.convertAndSendToUser(playerSession.getUserId(),"/info",message);
    }
}
