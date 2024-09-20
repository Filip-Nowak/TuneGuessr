package com.example.tuneguessrserver.response.websocket;

import com.example.tuneguessrserver.game.GameSongModel;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class MessageModel {
    private MessageInfo info;
    private Object message;

    public static MessageModel createUserInfo(Object message) {
        return MessageModel.builder().info(MessageInfo.USER_SESSION).message(message).build();
    }

    public static MessageModel createRoomInfo(Object message) {
        return MessageModel.builder().info(MessageInfo.ROOM_SESSION).message(message).build();
    }

    public static MessageModel createRoomCreationInfo(Object message) {
        return MessageModel.builder().info(MessageInfo.ROOM_CREATED).message(message).build();
    }

    public static MessageModel createNewPlayerJoinedInfo(Object message) {
        return MessageModel.builder().info(MessageInfo.NEW_PLAYER_JOINED).message(message).build();
    }

    public static MessageModel createJoinedRoomInfo(Object message) {
        return MessageModel.builder().info(MessageInfo.JOINED_ROOM).message(message).build();
    }

    public static MessageModel createPlayerLeftInfo(Object playerModel) {
        return MessageModel.builder().info(MessageInfo.PLAYER_LEFT).message(playerModel).build();
    }

    public static MessageModel createPlayerReadyInfo(Object playerModel) {
        return MessageModel.builder().info(MessageInfo.PLAYER_READY).message(playerModel).build();
    }

    public static MessageModel createGameStartInfo() {
        return MessageModel.builder().info(MessageInfo.GAME_START).message(null).build();
    }

    public static MessageModel createNextSongInfo(GameSongModel song) {
        return MessageModel.builder().info(MessageInfo.NEXT_SONG).message(
                Map.of(
                        "url", song.getUrl(),
                        "start", song.getStart()
                )
        ).build();
    }

    public static MessageModel createRoomErrorInfo(String error) {
        return MessageModel.builder().info(MessageInfo.ROOM_ERROR).message(error).build();
    }

    public static MessageModel createGameErrorInfo(String error) {
        return MessageModel.builder().info(MessageInfo.GAME_ERROR).message(error).build();
    }

    public static MessageModel createFinishInfo() {
        return MessageModel.builder().info(MessageInfo.FINISHED).message(null).build();
    }

    public static MessageModel createCorrectGuessInfo(String title) {
        return MessageModel.builder().info(MessageInfo.CORRECT_GUESS).message(title).build();
    }

    public static MessageModel createWrongGuessInfo() {
        return MessageModel.builder().info(MessageInfo.WRONG_GUESS).message(null).build();
    }
}
