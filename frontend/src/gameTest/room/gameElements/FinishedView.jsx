import React, { useEffect, useState } from "react";
import Online from "../../online/Online";

export default function FinishedView({ points, time, returnToLobby }) {
  const [finishedPlayers, setFinishedPlayers] = useState([]);
  const [stillPlayingPlayers, setStillPlayingPlayers] = useState([]);
  useEffect(() => {
    const players = Online.getRoom().getPlayers();
    const finished = players.filter((player) => player.finished);
    const playing = players.filter((player) => !player.finished);
    setFinishedPlayers(finished);
    setStillPlayingPlayers(playing);
    // Online.removeHandler("FINISHED", handleFinished);
    Online.setFinishedHandler(() => {
      console.log("finished in finished view");
      setStillPlayingPlayers((prevState) => {
        return [
          ...Online.getRoom()
            .getPlayers()
            .filter((player) => !player.finished),
        ];
      });
      setFinishedPlayers((prevState) => {
        return [
          ...Online.getRoom()
            .getPlayers()
            .filter((player) => player.finished),
        ];
      });
    });
  }, []);
  console.log(Online.getRoom());
  return (
    <div>
      <h1>Finished</h1>
      <div>Points: {points}</div>
      <div>Time: {time}</div>
      <div>
        leaderBoard
        <div>
          {finishedPlayers.map((player) => {
            return (
              <div>
                {player.nickname} - {player.score} ; {player.time}
              </div>
            );
          })}
          {stillPlayingPlayers.map((player) => {
            return <div>{player.nickname} - playing</div>;
          })}
        </div>
      </div>
      {Online.getUserId() === Online.getRoom().getHostId() &&
        stillPlayingPlayers.length === 0 && (
          <button onClick={returnToLobby}>Return to lobby</button>
        )}
    </div>
  );
}
