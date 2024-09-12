import React, { useEffect, useRef } from "react";
import Online from "../online/Online.js";

export default function CommandsContainer({ setConnected, setUpdated }) {
  const nicknameInput = useRef(null);
  const roomIdInput = useRef(null);
  const handleCreateUser = () => {
    console.log("create user");
    Online.createUser();
  };
  const handleSetSession = () => {
    console.log("set session");
    Online.setSession(nicknameInput.current.value);
  };
  const handeCreateRoom = () => {
    console.log("create room");
    Online.createRoom();
  };
  const handleConnect = () => {
    console.log("connect");
    Online.connect(() => {
      setUpdated(false);
      setConnected(true);
    });
  };
  const handleQuickConnect = () => {
    Online.quickConnect(() => {
      setUpdated(false);
      setConnected(true);
    });
  };
  const handleJoinRoom = () => {
    console.log("join room");
    Online.joinRoom(roomIdInput.current.value);
  };
  return (
    <div style={{ width: "100%", border: "5px black dashed" }}>
      CommandsContainer
      <div>
        <button onClick={handleCreateUser} style={{ backgroundColor: "gray" }}>
          create user
        </button>
      </div>
      <div>
        <button onClick={handleConnect} style={{ backgroundColor: "gray" }}>
          connect
        </button>
      </div>
      <div>
        nickname:
        <input ref={nicknameInput} style={{ border: "1px black solid" }} />
        <button onClick={handleSetSession} style={{ backgroundColor: "gray" }}>
          set session
        </button>
      </div>
      <div>
        <button onClick={handeCreateRoom} style={{ backgroundColor: "gray" }}>
          create room
        </button>
      </div>
      <div>
        room id:
        <input ref={roomIdInput} style={{ border: "1px black solid" }} />
        <button onClick={handleJoinRoom} style={{ backgroundColor: "gray" }}>
          join room
        </button>
      </div>
      <h1>quick connect</h1>
      <button onClick={handleQuickConnect} style={{ backgroundColor: "gray" }}>
        quick connect
      </button>
    </div>
  );
}
