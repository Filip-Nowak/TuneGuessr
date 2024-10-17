import React, { forwardRef, useEffect, useState } from 'react'
import YouTube from 'react-youtube';

function CustomPlayer({url, startingTime,handlePause,show,loaded,setLoaded},playerRef) {
  const [playing, setPlaying] = useState(false);

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
      console.log("playing");
        if (!loaded) {
            playerRef.current.pauseVideo();
            playerRef.current.unMute();
            setLoaded(true);
          }
    }
    const onPause = () => {
      handlePause();
        setPlaying(false)
        const videoLength = playerRef.current.getDuration();
    let start = startingTime * videoLength;
    if (videoLength - videoLength * startingTime < 30) {
      start = videoLength - 30;
    }
        playerRef.current.seekTo(start, true);
    }
  return (
   <div style={{display:show?"flex":"none",justifyContent:"center"}}>
    {url?<YouTube
            key={url}
            videoId={url.split("v=")[1]}
            opts={{
                height: "293",
                 width: "480",
                playerVars: {
                  autoplay: 0,
                },
            }}
            onReady={onPlayerReady}
            onPlay={()=>{
              if(!show)onPlay()}}
            onPause={
              ()=>{if(!show)onPause()}}
            onEnd={()=>{
              playerRef.current.pauseVideo();
              onPause();
            }}
          />:""}
   </div>
  )
}

export default forwardRef(CustomPlayer)