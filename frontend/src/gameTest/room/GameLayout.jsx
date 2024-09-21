import React, { useEffect, useState } from "react";
import Online from "../online/Online";
import SongPlayer from "./gameElements/SongPlayer";

export default function GameLayout() {
  const [url, setUrl] = useState(null);
  const [startingTime, setStartingTime] = useState(null);
  const [title, setTitle] = useState(null);
  const [artist, setArtist] = useState(null);
  useEffect(() => {
    Online.setNextSongHandler((msg) => {
      console.log("next song", msg);
      setUrl(msg.url);
      setStartingTime(msg.start);
      setTitle(null);
      setArtist(null);
    });
    Online.setCorrectGuessHandler((answer) => {
      console.log("correct guess", answer);
      if(answer.title){
        setTitle(answer.guess);
      }else{
        setArtist(answer.guess);
      }
    }
    );
    Online.setWrongGuessHandler(() => {
      console.log("incorrect guess");
    }
    );
    Online.readyToStart();
  }, []);
  const handleNext = () => {
    Online.next();
    // setUrl(null);
  }
  return (
    <div>
      GameLayout
      <SongPlayer url={url} startingTime={startingTime} title={title} artist={artist}/>
      <button onClick={handleNext}>Next</button>
    </div>
  );
}
