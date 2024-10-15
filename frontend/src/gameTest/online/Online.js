import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client";
import MessageHandler from "./MessageHandler";
import Room from "./Room";

class Online {
  #stompClient;
  #userId;
  #room;
  #roomSubscription;
  #userSubscription;
  #nickname;
  #ready = false;
  /**
   * @type {MessageHandler}
   */
  #messageHandler = new MessageHandler();
  async createUser() {
    const response = await fetch("https://localhost:8080/create-user");
    const data = await response.json();
    this.#userId = data.data.userId;
  }
  setSession(nickname) {
    if (this.#stompClient && this.#stompClient.connected) {
      this.#stompClient.publish({
        destination: "/app/user/set-session",
        body: JSON.stringify({
          userId: this.#userId,
          ...(nickname ? { nickname: nickname } : {}),
        }),
      });
    }
    return {};
  }
  getSession() {
    if (this.#stompClient && this.#stompClient.connected) {
      this.#stompClient.publish({
        destination: "/app/user/session",
        body: JSON.stringify(),
      });
    }
  }
  handleSessionChange;
  async connect(nickname, onConnect) {
    await this.createUser();
    const socket = new SockJS("https://localhost:8080/ws");
    const client = new Client({
      webSocketFactory: () => socket,
      onConnect: () => {
        this.#messageHandler = new MessageHandler();
        this.setHandlers();
        this.#userSubscription = client.subscribe(
          "/user/" + this.#userId + "/info",
          (message) => {
            const body = JSON.parse(message.body);
            console.log("got USER message:", body);
            this.#messageHandler.handle(body.info, body.message);
          }
        );
        this.setSession(nickname);

        onConnect();
      },
      onStompError: (error) => {
        console.error("STOMP error:", error);
      },
      onDisconnect: () => {
        console.log("disconnected");
      },
      onWebSocketClose: () => {
        console.log("socket closed");
      },
      onWebSocketError: (error) => {
        console.error("socket error:", error);
      },
    });

    client.activate();
    this.#stompClient = client;
  }

  createRoom(challengeId, gamemode) {
    this.#sendMessage(
      "/app/room/create",
      JSON.stringify({ challengeId: challengeId, gameMode: gamemode })
    );
  }

  setHandlers() {
    this.#messageHandler.addHandler("ROOM_CREATED", (message) => {
      this.#room = new Room(message);
      this.#roomSubscription = this.#stompClient.subscribe(
        "/room/" + this.#room.getId(),
        (message) => {
          const body = JSON.parse(message.body);
          console.log("got ROOM message:", body);
          this.#messageHandler.handle(body.info, body.message);
        }
      );
    });
    this.#messageHandler.addHandler("JOINED_ROOM", (message) => {
      this.#room = new Room(message);
      this.#roomSubscription = this.#stompClient.subscribe(
        "/room/" + this.#room.getId(),
        (message) => {
          const body = JSON.parse(message.body);
          console.log("got ROOM message:", body);
          this.#messageHandler.handle(body.info, body.message);
        }
      );
    });
    this.#messageHandler.addHandler("NEW_PLAYER_JOINED", (message) => {
      this.#room.addPlayer(message);
    });
    this.#messageHandler.addHandler("PLAYER_LEFT", (message) => {
      if (message.playerId === this.#userId) {
        this.#room = null;
      } else {
        this.#room.removePlayer(message);
        this.#room.setHostId(message.hostId);
      }
    });
    this.#messageHandler.addHandler("PLAYER_READY", (message) => {
      if (message.playerId === this.#userId) {
        this.#ready = message.ready;
      }
      this.#room.setPlayerReady(message.playerId, message.ready);
    });
    this.#messageHandler.addHandler("GAME_START", (message) => {
      this.#room.setInGame(true);
    });
    this.#messageHandler.addHandler("USER_SESSION", (message) => {
      this.#userId = message.userId;
      this.#nickname = message.nickname;
    });
    this.#messageHandler.addHandler("FINISHED", (message) => {
      console.log("player finished", message);
      this.#room.setPlayerFinished(message.id, message.score, message.time);
    });
    this.#messageHandler.addHandler("END_GAME", (message) => {
      this.#room.setInGame(false);
      for (let player of this.#room.getPlayers()) {
        player.finished = false;
        player.score = 0;
        player.time = 0;
        player.ready = false;
      }
    });
    this.#messageHandler.addHandler("ROOM_OPTIONS_CHANGED", (message) => {
      this.#room.setMode(message.gameMode);
      this.#room.setChallengeId(message.challengeId);
    });
  }

  setSessionUpdateHandler(handler) {
    this.#messageHandler.addHandler("USER_SESSION", handler);
  }
  setCreateRoomHandler(handler) {
    this.#messageHandler.addHandler("ROOM_CREATED", handler);
  }
  setJoinedRoomHandler(handler) {
    this.#messageHandler.addHandler("JOINED_ROOM", handler);
  }
  setNewPlayerJoinedHandler(handler) {
    this.#messageHandler.addHandler("NEW_PLAYER_JOINED", handler);
  }
  setPlayerLeftHandler(handler) {
    this.#messageHandler.addHandler("PLAYER_LEFT", handler);
  }
  setPlayerReadyHandler(handler) {
    this.#messageHandler.addHandler("PLAYER_READY", handler);
  }
  setGameStartedHandler(handler) {
    this.#messageHandler.addHandler("GAME_START", handler);
  }
  setNextSongHandler(handler) {
    this.#messageHandler.addHandler("NEXT_SONG", handler);
  }
  setRoomErrorHandler(handler) {
    this.#messageHandler.addHandler("ROOM_ERROR", handler);
  }
  setCorrectGuessHandler(handler) {
    this.#messageHandler.addHandler("CORRECT_GUESS", handler);
  }
  setWrongGuessHandler(handler) {
    this.#messageHandler.addHandler("WRONG_GUESS", handler);
  }
  setAnswerHandler(handler) {
    this.#messageHandler.addHandler("ANSWER", handler);
  }
  setFinishedHandler(handler) {
    this.#messageHandler.addHandler("FINISHED", handler);
  }
  setGameEndHandler(handler) {
    this.#messageHandler.addHandler("END_GAME", handler);
  }
  setRoomChangeHandler(handler) {
    this.#messageHandler.addHandler("ROOM_OPTIONS_CHANGED", handler);
  }
  getUserId() {
    return this.#userId;
  }
  joinRoom(roomId) {
    if (roomId === "") {
      throw new Error("room id is empty");
    }
    this.#sendMessage("/app/room/join", roomId);
  }
  // setRoomId(roomId) {
  //   if (this.#stompClient && this.#stompClient.connected) {
  //     this.#room.getId() = roomId;
  //     if (roomId === "") {
  //       this.#roomSubscription.unsubscribe();
  //       return;
  //     }
  //     this.#roomSubscription = this.#stompClient.subscribe(
  //       "/room/" + roomId,
  //       (message) => {
  //         const body = JSON.parse(message.body);
  //         this.#messageHandler.handle(body.info, body.message);
  //       }
  //     );
  //   } else {
  //     throw new Error("not connected");
  //   }
  // }
  isInRoom() {
    return !!this.#room;
  }
  leaveRoom() {
    this.#sendMessage("/app/room/leave", "");
  }
  ready(ready) {
    this.#sendMessage("/app/room/ready", ready.toString());
  }
  startGame() {
    this.#sendMessage("/app/room/start", "");
  }
  next() {
    this.#sendMessage("/app/game/next", "");
  }
  readyToStart() {
    this.#sendMessage("/app/game/ready-to-start", "");
  }
  getRoom() {
    return this.#room;
  }
  #sendMessage(destination, body) {
    if (this.#stompClient && this.#stompClient.connected) {
      console.log("sending message:", body, "\nto:\n", destination);
      this.#stompClient.publish({
        destination: destination,
        body: body,
      });
    } else {
      console.log("not connected");
    }
  }
  guessArtist(artist, time) {
    this.#sendMessage(
      "/app/game/guess",
      JSON.stringify({
        guess: artist,
        title: false,
        time: time,
      })
    );
  }
  guessTitle(title, time) {
    this.#sendMessage(
      "/app/game/guess",
      JSON.stringify({
        guess: title,
        title: true,
        time: time,
      })
    );
  }
  removeHandler(info) {
    this.#messageHandler.removeHandler(info);
  }
  clearHandlers() {
    this.#messageHandler.clearHandlers();
  }
  endGame() {
    this.#sendMessage("/app/game/end", "");
  }
  changeMode(mode) {
    this.#sendMessage("/app/room/change-mode", mode);
  }
  changeChallenge(challengeId) {
    this.#sendMessage("/app/room/change-challenge", challengeId);
  }
  disconnect(){
    this.#stompClient.deactivate();
    this.#nickname = null;
    this.#userId = null;
    this.#room = null;
    this.#ready = false;
    this.#messageHandler.clearHandlers();
    this.#stompClient = null;
  
  }
}
export default new Online();
