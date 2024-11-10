import React, { useEffect, useState } from "react";
import Online from "../../gameTest/online/Online";
import styles from "../room/finishedStyles.module.css";
export default function FinishedContent({
  points,
  time,
  returnToLobby,
  room,
  setRoom,
}) {
  const [finishedPlayers, setFinishedPlayers] = useState([]);
  const [stillPlayingPlayers, setStillPlayingPlayers] = useState([]);
  const [challengeName, setChallengeName] = useState(null);
  useEffect(() => {
    fetch("https://localhost:8080/api/challenge/" + room.getChallengeId())
      .then((response) => response.json())
      .then((data) => {
        setChallengeName(data.data.name);
      });
    const players = room.getPlayers();
    const finished = players.filter((player) => player.finished);
    const playing = players.filter((player) => !player.finished);
    finished.sort((a, b) => {
      if (a.score > b.score) {
        return -1;
      }
      if (a.score < b.score) {
        return 1;
      }
      return a.time - b.time;
    });
    setFinishedPlayers(finished);
    setStillPlayingPlayers(playing);
    // Online.removeHandler("FINISHED", handleFinished);
    Online.setFinishedHandler(() => {
      console.log("finished in finished view");
      setStillPlayingPlayers((prevState) => {
        const players = [
          ...room.getPlayers().filter((player) => !player.finished),
        ];
        players.sort((a, b) => {
          if (a.score > b.score) {
            return -1;
          }
          if (a.score < b.score) {
            return 1;
          }
          return a.time - b.time;
        });
        return players;
      });
      setFinishedPlayers((prevState) => {
        const players = [
          ...room.getPlayers().filter((player) => player.finished),
        ];
        players.sort((a, b) => {
          if (a.score > b.score) {
            return -1;
          }
          if (a.score < b.score) {
            return 1;
          }
          return a.time - b.time;
        });
        return players;
      });
    });
  }, []);
  console.log(Online.getRoom());
  const getTimeText = (time) => {
    let minutes = Math.floor(time / 6000);
    let seconds = Math.floor((time % 6000) / 100);
    let miliseconds = time % 100;
    let timeLabel = `${minutes === 0 ? "" : minutes + ":"}${
      seconds < 10 ? "0" + seconds : seconds
    }:${miliseconds < 10 ? "0" + miliseconds : miliseconds}`;
    return timeLabel;
  };
  return (
    <div className={styles.background}>
      <div className={styles.title}>finished</div>
      <div className={styles.challTitle}>{challengeName}</div>
      <div className={styles.playerScore}>
        <div className={styles.playerResults}>your results:</div>
        <div className={styles.score}>Score: {points}</div>
        <div className={styles.time}>Time: {getTimeText(time)}</div>
      </div>
      <div className={styles.otherLabel}>leaderboard:</div>
      <div className={styles.otherPlayersContainer}>
        <div className={styles.leaderboardHeader}>
          <div className={styles.leaderboardPlace}>place</div>
          <div className={styles.leaderboardName}>name</div>
          <div className={styles.leaderboardScore}>score</div>
          <div className={styles.leaderboardTime}>time</div>
        </div>
        <div className={styles.leaderboard}>
          {finishedPlayers.map((player, index) => {
            return (
              <div className={styles.leaderboardPlayer} key={index}>
                <div className={styles.leaderboardPlayerPlace}>{index + 1}</div>
                <div className={styles.leaderboardPlayerName}>
                  {player.nickname}
                </div>
                <div className={styles.leaderboardPlayerScore}>
                  {player.score}
                </div>
                <div className={styles.leaderboardPlayerTime}>
                  {getTimeText(player.time)}
                </div>
              </div>
            );
          })}
          {stillPlayingPlayers.length !== 0 && (
            <div className={styles.stillPlayingInfo}>still playing:</div>
          )}
          {stillPlayingPlayers.map((player, index) => {
            return (
              <div className={styles.leaderboardPlayer} key={index}>
                <div className={styles.leaderboardPlayerPlace}>-</div>
                <div className={styles.leaderboardPlayerName}>
                  {player.nickname}
                </div>
                <div className={styles.leaderboardPlayerScore}>-</div>
                <div className={styles.leaderboardPlayerTime}>-</div>
              </div>
            );
          })}
        </div>
      </div>
      {room.getHostId() === Online.getUserId() &&
        stillPlayingPlayers.length === 0 && (
          <button className={styles.returnButton} onClick={returnToLobby}>
            return to lobby
          </button>
        )}
    </div>
  );
}
