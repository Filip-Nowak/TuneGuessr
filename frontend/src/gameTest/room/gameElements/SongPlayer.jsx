import React, { useRef, useState } from "react";
import { set } from "react-hook-form";
import YouTube from "react-youtube";

export default function SongPlayer({ url, startingTime = 0.3 }) {
  const [playing, setPlaying] = useState(false);
  const playerRef = useRef(null);
  const [time, setTime] = useState(0);
  const timer = useRef(null);
  const [firstTry, setFirstTry] = useState(true);
  const opts = {
    height: "390",
    width: "640",
    playerVars: {
      autoplay: 0,
    },
  };

  const onPlayerReady = (event) => {
    playerRef.current = event.target;
    const videoLength = playerRef.current.getDuration();
    playerRef.current.seekTo(startingTime * videoLength, true);
    playerRef.current.cueVideoById(url.split("v=")[1]);
  };
  const playVideo = () => {
    setPlaying(true);
    const videoLength = playerRef.current.getDuration();

    playerRef.current.seekTo(startingTime * videoLength, true);
    playerRef.current.playVideo();
    if (!firstTry) {
      timer.current = setInterval(() => {
        setTime((prev) => {
          return prev + 1;
        });
      }, 100);
    }
  };

  const pauseVideo = () => {
    timer.current && clearInterval(timer.current);
    playerRef.current.pauseVideo();
  };

  return (
    <div>
      <div style={{ display: "none" }}>
        <YouTube
          videoId={url.split("v=")[1]}
          opts={opts}
          onReady={onPlayerReady}
          onPlay={() => {
            if (firstTry) {
              timer.current = setInterval(() => {
                setTime((prev) => {
                  return prev + 1;
                });
              }, 100);
              setFirstTry(false);
            }
          }}
          onPause={() => setPlaying(false)}
        />
      </div>
      <div>time: {time}</div>
      <div
        style={{
          height: "50vh",
          display: "flex",
          textAlign: "center",
          justifyContent: "center",
          alignItems: "center",
          fontSize: "8rem",
        }}
      >
        {playing ? (
          <i className="fa-solid fa-music"></i>
        ) : (
          <div>
            <i className="fa-solid fa-play"></i>
          </div>
        )}
      </div>
      <div>
        <button onClick={playVideo}>Play</button>
        <button onClick={pauseVideo}>Pause</button>
      </div>
    </div>
  );
}
