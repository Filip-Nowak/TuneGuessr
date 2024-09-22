import React, { useEffect, useState } from "react";
import ConnectLayout from "./ConnectLayout";
import JoinRoomLayout from "./JoinRoomLayout";
import Online from "../online/Online";
import Room from "../online/Room";
import RoomLayout from "./RoomLayout";
import GameLayout from "./GameLayout";
import { set } from "react-hook-form";

export default function RoomPage() {
  const [connected, setConnected] = useState(false);
  const [inRoom, setInRoom] = useState(false);
  const [inGame, setInGame] = useState(false);
  useEffect(() => {
    Online.setGameEndHandler(() => {
      console.log("game ended");
      setInGame(false);
    });
  }, []);

  return (
    <div style={{ backgroundColor: "#333", height: "100vh", color: "white" }}>
      {connected ? (
        <div>
          {inRoom ? (
            <div>
              {" "}
              {!inGame ? (
                <RoomLayout setInGame={setInGame} setInRoom={setInRoom} />
              ) : (
                <GameLayout setInGame={setInGame} />
              )}
            </div>
          ) : (
            <JoinRoomLayout setInRoom={setInRoom} />
          )}
        </div>
      ) : (
        <ConnectLayout setConnected={setConnected} />
      )}
    </div>
  );
}
