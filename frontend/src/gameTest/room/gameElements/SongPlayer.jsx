import React, { useEffect, useRef, useState } from "react";
import { set } from "react-hook-form";
import YouTube from "react-youtube";
import Online from "../../online/Online";

export default function SongPlayer({
  url,
  startingTime,
  title,
  artist,
  time,
  setTime,
}) {
  const [playing, setPlaying] = useState(false);
  const playerRef = useRef(null);
  const timer = useRef(null);
  const [firstTry, setFirstTry] = useState(true);
  const [loadingVideo, setLoadingVideo] = useState(true);
  const guessArtistRef = useRef(null);
  const guessTitleRef = useRef(null);
  const opts = {
    height: "390",
    width: "640",
    playerVars: {
      autoplay: 0,
    },
  };
  useEffect(() => {
    setLoadingVideo(true);
    setPlaying(false);
    setFirstTry(true);
  }, [url]);

  const onPlayerReady = (event) => {
    playerRef.current = event.target;
    playerRef.current.mute();
    const videoLength = playerRef.current.getDuration();
    let start = startingTime * videoLength;
    if (videoLength - videoLength * startingTime < 30) {
      start = videoLength - 30;
    }
    playerRef.current.seekTo(start, true);
    playerRef.current.playVideo();
  };
  const playVideo = () => {
    setPlaying(true);
    const videoLength = playerRef.current.getDuration();
    let start = startingTime * videoLength;
    if (videoLength - videoLength * startingTime < 30) {
      start = videoLength - 30;
    }
    playerRef.current.seekTo(start, true);
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

  const guessArtist = () => {
    console.log("dupa");
    console.log(guessArtistRef.current.value);
    console.log(time);

    Online.guessArtist(guessArtistRef.current.value, time);
  };
  const guessTitle = () => {
    console.log(time);
    Online.guessTitle(guessTitleRef.current.value, time);
  };

  return (
    <div>
      <div style={{ display: "none" }}>
        {url ? (
          <YouTube
            key={url}
            videoId={url.split("v=")[1]}
            opts={opts}
            onReady={onPlayerReady}
            onPlay={() => {
              if (firstTry) {
                playerRef.current.pauseVideo();
                playerRef.current.unMute();
                setFirstTry(false);
                setLoadingVideo(false);
              }
            }}
            onPause={() => setPlaying(false)}
          />
        ) : (
          ""
        )}
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
        {loadingVideo ? (
          <div>Loading Video...</div>
        ) : (
          <div>
            {playing ? (
              <button onClick={pauseVideo}>Pause</button>
            ) : (
              <button onClick={playVideo}>Play</button>
            )}
          </div>
        )}
      </div>
      <div>
        artist:{" "}
        {artist !== null ? (
          <div>
            {artist}
            <br />
            <br />
          </div>
        ) : (
          <div>
            <input style={{ color: "black" }} ref={guessArtistRef} />
            <button onClick={guessArtist}>guess</button> <br />
            <br />
          </div>
        )}
        title:{" "}
        {title !== null ? (
          <div>
            {title}
            <br />
          </div>
        ) : (
          <div>
            <input style={{ color: "black" }} ref={guessTitleRef} />
            <button onClick={guessTitle}>guess</button>
          </div>
        )}
      </div>
    </div>
  );
}
