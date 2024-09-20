import React, { useEffect } from "react";
import Online from "../online/Online";
import SongPlayer from "./gameElements/SongPlayer";

export default function GameLayout() {
  useEffect(() => {
    Online.setNextSongHandler((url) => {
      console.log("next song", url);
    });
    Online.readyToStart();
  }, []);
  return (
    <div>
      GameLayout
      <SongPlayer url="https://www.youtube.com/watch?v=rDMBzMcRuMg" />
    </div>
  );
}
