import React, { useState } from "react";
import ConnectLayout from "./ConnectLayout";
import JoinRoomLayout from "./JoinRoomLayout";
import Online from "../online/Online";
import Room from "../online/Room";
import RoomLayout from "./RoomLayout";
import GameLayout from "./GameLayout";

export default function RoomPage() {
  const [connected, setConnected] = useState(false);
  const [inRoom, setInRoom] = useState(false);
  const [inGame, setInGame] = useState(false);
  console.log(Online)

  return <div style={{backgroundColor:"#333",height:"100vh",color:"white"}}>
    {
      connected?
      <div>
        {
          inRoom?
           <div> {
              !inGame?
              <RoomLayout setInGame={setInGame} setInRoom={setInRoom} />:
              <GameLayout setInGame={setInGame} />
            }</div>
          :
          <JoinRoomLayout setInRoom={setInRoom} />
        }
        </div>:
<ConnectLayout setConnected={setConnected} />
    }
  </div>;
}
