import React, { useRef } from "react";

export default function CommandsContainer() {
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
  return (
    <div style={{ width: "100%", border: "5px black dashed" }}>
      CommandsContainer
      <div>
        <button onClick={handleCreateUser} style={{ backgroundColor: "gray" }}>
          create user
        </button>
      </div>
      <div>
        nickname:
        <input ref={nicknameInput} />
        <button onClick={handleSetSession} style={{ backgroundColor: "gray" }}>
          set session
        </button>
      </div>
    </div>
  );
}
