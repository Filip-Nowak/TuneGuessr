import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client";

class Online {
  #stompClient;
  #userId;
  connect(onConnect) {
    const socket = new SockJS("http://localhost:8080/ws?name=" + name);
    const client = new Client({
      webSocketFactory: () => socket,
      onConnect: () => {
        client.subscribe("/topic/messages", (message) => {
          console.log("message:", message);
        });
        console.log("/xdd/"+this.#userId)
        client.subscribe("/user/"+this.#userId+"/info", (message) => {
          console.log("got message");
          console.log(message);
          const body = JSON.parse(message.body);
          console.log(body);
          this.handleSessionChange(body);
        })
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
    console.log("response:", response);
    const data = await response.json();
    this.#userId = data.data.userId;
    console.log("data:", this.#userId);
  }
  setSession(nickname) {
    console.log("setId");
    console.log(this);
    if (this.#stompClient && this.#stompClient.connected) {
      this.#stompClient.publish({
        destination: "/app/user/set-session",
        body: JSON.stringify({
          userId: this.#userId,
          ...(nickname ? { nickname: nickname } : {}),
        }),
      });
    }
    return {}
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
  handleSessionChange;
}
export default new Online();
