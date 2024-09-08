import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client";

class Online {
  #stompClient;
  #userId;
  join(name, onConnect) {
    const socket = new SockJS("http://localhost:8080/ws?name=" + name);
    const client = new Client({
      webSocketFactory: () => socket,
      onConnect: () => {
        console.log("Connected, name:", name);
        client.subscribe("/topic/messages", (message) => {
          console.log("message:", message);
        });
        onConnect();
      },
      onStompError: (error) => {
        console.error("STOMP error:", error);
      },
    });

    client.activate();
    this.#stompClient = client;
  }
  test() {
    if (this.#stompClient && this.#stompClient.connected) {
      this.#stompClient.publish({
        destination: "/app/chat",
        body: JSON.stringify({ message: "Hello, World!" }),
      });
    }
  }
  async createUser() {
    console.log(this);
    const response = await fetch("http://localhost:8080/create-user");
    const data = await response.json();
    this.#userId = data.data.userId;
    console.log("data:", this.#userId);
  }
  setId(nickname) {
    console.log("setId");
    console.log(this);
    if (this.#stompClient && this.#stompClient.connected) {
      this.#stompClient.publish({
        destination: "/app/set-id",
        body: JSON.stringify({
          userId: this.#userId,
          ...(nickname ? { nickname: nickname } : {}),
        }),
      });
    }
  }
  getSession() {
    console.log("getSession");
    if (this.#stompClient && this.#stompClient.connected) {
      this.#stompClient.publish({
        destination: "/app/user/session",
        body: JSON.stringify(),
      });
    }
  }
}
export default new Online();
