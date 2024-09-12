import React from "react";
import Player from "./Player";

export default function RoomView({ players = [], hostId }) {
  return (
    <div>
      RoomViwe
      {players.map((player, index) => {
        return <Player hostId={hostId} key={index} player={player} />;
      })}
    </div>
  );
}
