import React from "react";
import Player from "./Player";
import Online from "../online/Online";

export default function RoomView({ players = [], hostId }) {
  const handleReady = () => {
    Online.ready();
  };
  const handleLeave = () => {
    Online.leaveRoom();
  };
  const handleGameReady = () => {
    Online.gameReady();
  }
  return (
    <div>
      RoomViwe
      {players.length === 0
        ? "no players"
        : players.map((player, index) => {
            return <Player hostId={hostId} key={index} player={player} />;
          })}
      {Online.isInRoom() ? (
        <div
          style={{
            display: "flex",
            justifyContent: "space-evenly",
            fontSize: "2rem",
          }}
        >
          <button style={{ backgroundColor: "gray" }} onClick={handleReady}>
            ready
          </button>
          <button style={{ backgroundColor: "gray" }} onClick={handleLeave}>
            leave
          </button>
          {
            <div>
              {Online.getUserId() === hostId ? (
                <button
                  style={{ backgroundColor: "gray" }}
                  onClick={() => {
                    Online.startGame();
                  }}
                >
                  start
                </button>
              ) : (
                ""
              )}
            </div>
          }
        </div>
      ) : (
        ""
      )}
      <button onClick={handleGameReady}>
        game ready
      </button>
    </div>
  );
}
