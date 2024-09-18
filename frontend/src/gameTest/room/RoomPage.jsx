import React, { useState } from "react";
import ConnectLayout from "./ConnectLayout";
import JoinRoomLayout from "./JoinRoomLayout";
import Online from "../online/Online";
import Room from "../online/Room";
import RoomLayout from "./RoomLayout";

export default function RoomPage() {
  const [connected, setConnected] = useState(false);
  const [inRoom, setInRoom] = useState(false);
  console.log(Online)

  return <div style={{backgroundColor:"#333",height:"100vh",color:"white"}}>
    {
      connected?
      <div>
        {
          inRoom?
          <RoomLayout setInRoom={setInRoom}/>:
          <JoinRoomLayout setInRoom={setInRoom} />
        }
        </div>:
<ConnectLayout setConnected={setConnected} />
    }
  </div>;
}
