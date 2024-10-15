import React, { useEffect, useRef, useState } from "react";
import Online from "../../gameTest/online/Online";
import FinishedView from "../../gameTest/room/gameElements/FinishedView";
import CustomPlayer from "./CustomPlayer";


export default function GameContent() {
  const [url, setUrl] = useState(null);
  const [startingTime, setStartingTime] = useState(null);
  const [title, setTitle] = useState(null);
  const [artist, setArtist] = useState(null);
  const [lives, setLives] = useState(3);
  const [finished, setFinished] = useState(false);
  const [points, setPoints] = useState(0);
  const [time, setTime] = useState(0);
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

    Online.readyToStart();
    return () => {
      Online.removeHandler("NEXT_SONG");
      Online.removeHandler("CORRECT_GUESS");
      Online.removeHandler("WRONG_GUESS");
      Online.removeHandler("ANSWER");
      Online.removeHandler("FINISHED");

    }
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
          <CustomPlayer url={url} ref={playerRef}/>

        </div>
      )}
    </div>
  );
}
