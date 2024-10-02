import React, { useState, useEffect } from "react";
import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";

const WebSocketDemo = () => {
  const [messages, setMessages] = useState([]);
  const [stompClient, setStompClient] = useState(null);

  useEffect(() => {
    // Inicjalizacja SockJS i STOMP
    const socket = new SockJS("http://localhost:8080/ws");
    const client = new Client({
      webSocketFactory: () => socket,
      onConnect: () => {
        console.log("Connected");
        client.subscribe("/topic/messages", (message) => {
          setMessages((prevMessages) => [
            ...prevMessages,
            JSON.parse(message.body).content,
          ]);
        });
      },
      onStompError: (error) => {
        console.error("STOMP error:", error);
      },
    });

    client.activate();
    setStompClient(client);

    // Czyszczenie połączenia przy unmount
    return () => {
      client.deactivate();
    };
  }, []);

  const sendMessage = () => {
    if (stompClient && stompClient.connected) {
      stompClient.publish({
        destination: "/app/chat",
        body: JSON.stringify({ message: "Hello, World!" }),
      });
    }
  };

  return (
    <div>
      <h1>WebSocket Test</h1>
      <button onClick={sendMessage}>Send Message</button>
      <div>
        <h2>Messages:</h2>
        <ul>
          {messages.map((msg, index) => (
            <li key={index}>{msg}</li>
          ))}
        </ul>
      </div>
    </div>
  );
};

export default WebSocketDemo;
