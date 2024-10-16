import React, { useEffect, useRef, useState } from "react";
import Online from "../../gameTest/online/Online";
import FinishedView from "../../gameTest/room/gameElements/FinishedView";
import CustomPlayer from "./CustomPlayer";
import styles from "./gameStyles.module.css";

export default function GameContent({ room, setRoom }) {
  const [url, setUrl] = useState(null);
  const [startingTime, setStartingTime] = useState(null);
  const [title, setTitle] = useState(null);
  const [artist, setArtist] = useState(null);
  const [lives, setLives] = useState(3);
  const [finished, setFinished] = useState(false);
  const [points, setPoints] = useState(0);
  const [time, setTime] = useState(0);
  const [challTitle, setChallTitle] = useState(null);
  const [playing, setPlaying] = useState(false);
  const playerRef = useRef(null);

  useEffect(() => {
    Online.setNextSongHandler((msg) => {
      console.log("next song", msg);
      setUrl(msg.url);
      setStartingTime(msg.start);
      setTitle(null);
      setArtist(null);
      setLives(3);
    });
    Online.setCorrectGuessHandler((answer) => {
      console.log("correct guess", answer);
      if (answer.title) {
        setTitle(answer.guess);
      } else {
        setArtist(answer.guess);
      }
      setPoints((prev) => prev + answer.points);
    });
    Online.setWrongGuessHandler(() => {
      setLives((prev) => prev - 1);
      console.log("incorrect guess");
    });
    Online.setAnswerHandler((answer) => {
      console.log("answer", answer);
      setTitle(answer.title);
      setArtist(answer.artist);
      setLives(0);
    });
    Online.setFinishedHandler(handleFinished);
    fetch(
      "https://localhost:8080/api/challenge/" +
        Online.getRoom().getChallengeId()
    )
      .then((response) => response.json())
      .then((data) => {
        setChallTitle(data.data.name);
      });
    Online.readyToStart();
    return () => {
      Online.removeHandler("NEXT_SONG");
      Online.removeHandler("CORRECT_GUESS");
      Online.removeHandler("WRONG_GUESS");
      Online.removeHandler("ANSWER");
      Online.removeHandler("FINISHED");
    };
  }, []);
  const handleFinished = (msg) => {
    const players = Online.getRoom().getPlayers();
    console.log(msg);
    console.log(msg.id, Online.getUserId());
    console.log();
    if (msg.id === Online.getUserId()) {
      setFinished(true);
      setPoints(msg.score);
      setTime(msg.time);
    }
  };

  const handleNext = () => {
    Online.next();
  };
  const returnToLobby = () => {
    Online.endGame();
  };
  let timeLabel =
    Math.floor(time / 60) +
    ":" +
    (time % 60 < 10 ? "0" + (time % 60) : time % 60);

  return (
    <div>
      {finished ? (
        <FinishedView
          points={points}
          time={time}
          handleFinished={handleFinished}
          returnToLobby={returnToLobby}
        />
      ) : (
        <div>
          <div className={styles.background}>
            <i style={{ opacity: "1" }} className="fa-solid fa-play"></i>
          </div>
          <CustomPlayer url={url} ref={playerRef} />
          <div style={{ height: "1rem" }}></div>
          <div className={styles.challTitle}>{challTitle}</div>
          <div className={styles.gameContainer}>
            <div className={styles.score}>score: {points}</div>
            <div className={styles.timer}> {timeLabel}</div>
            <div className={styles.answerBox}>
              <div>click timer to {playing ? "pause " : "play "} song</div>
              <div className={styles.lives}>
                {" "}
                {lives > 0
                  ? Array.from({ length: lives }).map((_, i) => (
                      <i className="fa-solid fa-heart"></i>
                    ))
                  : ""}
              </div>

              <div>
                <div className={styles.inputName}>title</div>
                <div className={styles.inputContainer}>
                  {" "}
                  <input className={styles.input} />{" "}
                  <button className={styles.checkButton}>check</button>
                </div>
              </div>
              <div>
                <div className={styles.inputName}>artist</div>
                <div className={styles.inputContainer}>
                  {" "}
                  <input className={styles.input} />{" "}
                  <button className={styles.checkButton}>check</button>
                </div>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}
