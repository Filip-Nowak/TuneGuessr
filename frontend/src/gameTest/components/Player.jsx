import React from "react";
import Online from "../online/Online";

export default function Player({ player, hostId }) {
  const { nickname, ready, id } = player;
  return (
    <div
      style={{
        width: "100%",
        border: "1px black dotted",
        backgroundColor: id === Online.getUserId() ? "lime" : "white",
        color: id === hostId ? "red" : "black",
      }}
    >
      <div>nickname: {nickname}</div>
      <div>ready: {ready.toString()}</div>
    </div>
  );
}
