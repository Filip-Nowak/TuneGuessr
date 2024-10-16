import React, { forwardRef, useEffect, useState } from 'react'
import YouTube from 'react-youtube';

function CustomPlayer({url},playerRef) {
  const [playing, setPlaying] = useState(false);
  const [loaded, setLoaded] = useState(false);

  useEffect(() => {
    setPlaying(false);
    setLoaded(false);
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
    const onPlay = () => {
        if (!loaded) {
            playerRef.current.pauseVideo();
            playerRef.current.unMute();
            setLoaded(true);
          }
    }
    const onPause = () => {
        setPlaying(false)
    }
  return (
   <div style={{display:"none"}}>
    {url?<YouTube
            key={url}
            videoId={url.split("v=")[1]}
            opts={{
                height: "390",
                 width: "640",
                playerVars: {
                  autoplay: 0,
                },
            }}
            onReady={onPlayerReady}
            onPlay={onPlay}
            onPause={onPause}
          />:""}
   </div>
  )
}

export default forwardRef(CustomPlayer)