import React, { useRef } from 'react'
import { useState } from 'react';
import { useLoaderData } from 'react-router-dom';
import RoomLayout from '../gameTest/room/RoomLayout';
import GameLayout from '../gameTest/room/GameLayout';
import RoomContent from '../components/room/RoomContent';
import Online from '../gameTest/online/Online';
import { useEffect } from 'react';
import CustomPlayer from '../components/room/CustomPlayer';
import GameContent from '../components/room/GameContent';

export default function RoomPage() {
  const loaderData = useLoaderData();
  const [room, setRoom] = useState(loaderData.room);
  const [inGame, setInGame] = useState(false);
  useEffect(() => {
    Online.setPlayerReadyHandler(handlePlayerReady);
    Online.setNewPlayerJoinedHandler(handlePlayerJoined);
    Online.setPlayerLeftHandler(handlePlayerLeft);
    Online.setGameStartedHandler(handleStartGame);
}, []);
const handlePlayerReady = () => {
    setRoom(Online.getRoom().clone());
}
const handlePlayerJoined = () => {
    setRoom(Online.getRoom().clone());
}
const handlePlayerLeft = (info) => {
  console.log("player left",info);
    if(Online.getUserId()===info.playerId){
        // setInRoom(false);
    }else{
        setRoom(Online.getRoom().clone());
    }
    
}
const handleStartGame = () => {
  setInGame(true);
}
  return (
    <div>
              {" "}
              {!inGame ? (
                <RoomContent room={room} setInGame={setInGame} setRoom={setRoom} />
              ) : (
                <GameContent room={room} setRoom={setRoom} setInGame={setInGame} />
              )}
            </div>
  )
}
