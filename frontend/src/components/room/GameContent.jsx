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
  const [videoLoaded, setVideoLoaded] = useState(false);
  const artistInput = useRef(null);
  const titleInput = useRef(null);
  const playerRef = useRef(null);
  const timeout = useRef(null);
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
  useEffect(() => {
    if (title && artist) {
      setLives(0);
    }
  }, [title, artist]);
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

  const handleTimerClick = () => {
    if(artist&&title)
      return
    if(!videoLoaded)
      return
    if (playing) {
      onPause();
    } else {
      playerRef.current.playVideo();
      timeout.current = setInterval(() => {
        setTime((prev) => prev + 1);
      }, 10);
      setPlaying(true);
    }
  };
  const onPause = () => {
    setPlaying(false);
    playerRef.current.pauseVideo();
    clearInterval(timeout.current);
  };
  const handleArtistGuess = () => {
    const guess = artistInput.current.value;
    Online.guessArtist(guess);
  };
  const handleTitleGuess = () => {
    const guess = titleInput.current.value;
    Online.guessTitle(guess);
  };
  const handleForfeit = () => {
    console.log("forfeit");
    Online.forfeit();
  };
  let minutes = Math.floor(time / 6000);
  let seconds = Math.floor((time % 6000) / 100);
  let miliseconds = time % 100;
  let timeLabel = `${minutes === 0 ? "" : minutes + ":"}${
    seconds < 10 ? "0" + seconds : seconds
  }:${miliseconds < 10 ? "0" + miliseconds : miliseconds}`;

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
          <div
            className={styles.background}
            style={{ backgroundColor: playing ? "rgb(129, 9, 9)" : "" }}
          >
            <i
              style={{ opacity: "1", color: playing ? "rgb(100, 9, 9)" : "" }}
              className={playing ? "fa-solid fa-pause" : "fa-solid fa-play"}
            ></i>
          </div>
          <div style={{ height: "1rem" }}></div>
          <div className={styles.challTitle}>{challTitle}</div>
          <div className={styles.gameContainer}>
            <div className={styles.score}>score: {points}</div>
            <div className={styles.timer +" "+ (title&&artist?styles.smallTimer:"")} onClick={handleTimerClick}>
              {" "}
              {timeLabel}
            </div>
            <div className={styles.lives} style={{display
            :lives>0?"block":"none"
            }}>
              {" "}
              {lives > 0
                ? Array.from({ length: lives }).map((_, i) => (
                  <i className="fa-solid fa-heart"></i>
                ))
                : ""}
            </div>
            <div style={{position:"relative",display:"flex",justifyContent:"center",alignItems:"center"}}>
                <div className={styles.nextButton} style={{display:artist&&title?"flex":"none",}} onClick={handleNext}>
                <i className="fa-solid fa-angles-right"></i>
                </div>

            <CustomPlayer
              url={url}
              ref={playerRef}
              startingTime={0.99}
              handlePause={onPause}
              show={artist&&title}
              loaded={videoLoaded}
              setLoaded={setVideoLoaded}
            /></div>
            {
              title&&artist?
              <div className={styles.answerBox} style={{width:"90vw"}}>
                <div className={styles.answerLabel}>answer:</div>
                <div className={styles.answer}>{artist} - {title}</div>
              </div>
            :
            playing ? (
              <div className={styles.playingInfo}>click timer to stop</div>
            ) : (
              <div className={styles.answerBox}>
                <div className={styles.forfeitButton} onClick={handleForfeit}>
                  <i className="fa-regular fa-flag"></i>
                  </div>
                <div>
                {videoLoaded
                ?
                "click timer to "+ (playing ? "pause " : "play ")+" song":"loading video..."
                }
                   </div>

                <div>
                  <div className={styles.inputName}>title</div>
                  {
                    title?
                    <div className={styles.inputContainer} >
                      <div className={styles.correctAnswer}>{title} <i style={{color:"green"}} class="fa-solid fa-circle-check"></i></div>

                      </div>:<div className={styles.inputContainer}>
                    {" "}
                    <input className={styles.input} ref={titleInput} />{" "}
                    <button
                      className={styles.checkButton}
                      onClick={handleTitleGuess}
                    >
                      check
                    </button>
                  </div>}
                </div>
                <div>
                  <div className={styles.inputName}>artist</div>
                  {
                    artist?
                    <div className={styles.inputContainer} >
                      <div className={styles.correctAnswer}>{artist} <i style={{color:"green"}} class="fa-solid fa-circle-check"></i></div>

                      </div>
                    :<div className={styles.inputContainer}>
                    
                    <input className={styles.input} ref={artistInput} />{" "}
                    <button
                      className={styles.checkButton}
                      onClick={handleArtistGuess}
                    >
                      check
                    </button>
                  </div>}
                </div>
              </div>
            )}
          </div>
        </div>
      )}
    </div>
  );
}
