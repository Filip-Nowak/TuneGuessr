import React, { useEffect, useState } from "react";
import Online from "../online/Online";
import SongPlayer from "./gameElements/SongPlayer";
import { set } from "react-hook-form";
import FinishedView from "./gameElements/FinishedView";

export default function GameLayout() {
  const [url, setUrl] = useState(null);
  const [startingTime, setStartingTime] = useState(null);
  const [title, setTitle] = useState(null);
  const [artist, setArtist] = useState(null);
  const [lives, setLives] = useState(3);
  const [finished, setFinished] = useState(false);
  const [points, setPoints] = useState(0);
  const [time, setTime] = useState(0);

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

    Online.readyToStart();
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
  return (
    <div>
      GameLayout
      {finished ? (
        <FinishedView
          points={points}
          time={time}
          handleFinished={handleFinished}
          returnToLobby={returnToLobby}
        />
      ) : (
        <div>
          <br /> points: {points}
          <SongPlayer
            time={time}
            setTime={setTime}
            url={url}
            startingTime={startingTime}
            title={title}
            artist={artist}
            setStartingTime={setStartingTime}
          />
          {title !== null && artist !== null ? (
            <button onClick={handleNext}>Next</button>
          ) : (
            <div>lives: {lives}</div>
          )}
        </div>
      )}
    </div>
  );
}
