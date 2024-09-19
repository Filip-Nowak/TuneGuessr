import React, { useEffect, useRef } from "react";
import Online from "../online/Online";
import { set } from "react-hook-form";

export default function JoinRoomLayout({ setInRoom }) {
  const roomIdInput = useRef(null);
  const challengeIdInput = useRef(null);
  const challengeTypeInput = useRef(null);
  useEffect(() => {
    Online.setCreateRoomHandler(() => {
      setInRoom(true);
    });
    Online.setJoinedRoomHandler(() => {
      setInRoom(true);
    });
  }, []);
  const handleJoinRoom = () => {
    Online.joinRoom(roomIdInput.current.value);
  };

  const handleCreateRoom = () => {
    Online.createRoom(
      challengeIdInput.current.value,
      challengeTypeInput.current.value
    );
  };
  return (
    <div
      style={{
        display: "flex",
        justifyContent: "center",
        height: "100vh   ",
        alignItems: "center",
      }}
    >
      <div style={{ border: "1px white solid", width: "30%", height: "20vh" }}>
        <div
          style={{ display: "flex", justifyContent: "center", padding: "10px" }}
        >
          <input
            ref={roomIdInput}
            placeholder="Enter Room Code"
            style={{ width: "100%", color: "black" }}
          />
        </div>
        <div
          style={{ display: "flex", justifyContent: "center", padding: "10px" }}
        >
          <button
            style={{ width: "100%", backgroundColor: "gray" }}
            onClick={handleJoinRoom}
          >
            Join Room
          </button>
        </div>
      </div>
      <div style={{ border: "1px white solid", width: "30%", height: "20vh" }}>
        <input
          ref={challengeIdInput}
          defaultValue="4"
          type="number"
          placeholder="Enter Challenge id"
          style={{ width: "100%", color: "black" }}
        />
        <select
          style={{ width: "100%", color: "black" }}
          ref={challengeTypeInput}
          defaultValue={"CLASSIC"}
        >
          <option value="CLASSIC">Classic</option>
          <option value="SURVIVAL">Survival</option>
          <option value="MULTIPLAYER">Multiplayer</option>
        </select>
        <button
          onClick={handleCreateRoom}
          style={{ width: "100%", backgroundColor: "gray" }}
        >
          Create Room
        </button>
      </div>
    </div>
  );
}
