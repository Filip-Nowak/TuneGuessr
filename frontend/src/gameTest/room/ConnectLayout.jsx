import React, { useEffect, useRef } from "react";
import Online from "../online/Online";

export default function ConnectLayout({ setConnected }) {
  const nicknameInput = useRef(null);
  const handleConnect = () => {
    Online.connect(nicknameInput.current.value, () => {
      setConnected(true);
      Online.setRoomErrorHandler((error) => {
        console.log("room error:", error);
      });
    });
  };

  return (
    <div
      style={{
        display: "flex",
        width: "100%",
        alignItems: "center",
        backgroundColor: "#333",
        color: "white",
        justifyContent: "center",
        height: "100vh",
        flexDirection: "column",
      }}
    >
      <div>enter nickname:</div>
      <input
        ref={nicknameInput}
        style={{ border: "1px black solid", color: "black" }}
      />
      <button style={{ backgroundColor: "gray" }} onClick={handleConnect}>
        connect
      </button>
    </div>
  );
}
