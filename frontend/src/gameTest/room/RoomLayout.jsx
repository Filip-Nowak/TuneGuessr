import React, { useState } from 'react'
import Online from '../online/Online';
import { useEffect } from 'react';

export default function RoomLayout({setInRoom}) {
    const [room, setRoom] = useState(Online.getRoom());
    const [challengeName, setChallengeName] = useState("");
    const [ready, setReady] = useState(false);
    useEffect(() => {
        fetch("http://localhost:8080/api/challenge/"+room.getChallengeId())
        .then(response => response.json())
        .then(data => {
            console.log(data)
            setChallengeName(data.data.name);
        })
        Online.setPlayerReadyHandler(handlePlayerReady);
        Online.setNewPlayerJoinedHandler(handlePlayerJoined);
        Online.setPlayerLeftHandler(handlePlayerLeft);
    }, []);
    const handlePlayerReady = () => {
        setRoom(Online.getRoom().clone());
    }
    const handlePlayerJoined = () => {
        setRoom(Online.getRoom().clone());
    }
    const handlePlayerLeft = (info) => {
        if(Online.getUserId()===info.playerId){
            setInRoom(false);
        }else{
            setRoom(Online.getRoom().clone());
        }
        
    }
    console.log(room);
    console.log(Online);
  return (
    <div style={
        {
            display: "flex",
            justifyContent: "center",
            height: "100vh",
            alignItems: "center",
            flexDirection: "column"
        }
    }>
        <span style={{fontSize:"3rem"}}>Room Code: {room.getId()}
        </span>
        <span>challenge: {challengeName}</span>
        <span>Players:</span>
        <div style={{display:"flex",flexDirection:"column",width:"100%",textAlign:"center",alignItems:"center"}}>
            {room.getPlayers().map(player => {
                return <span style={{
                    color:player.ready?"green":"",
                    border: room.getHostId()===player.id?"1px solid gold":"",
                    padding:"5px",
                    width:"50%",
                }}>{player.nickname}
                {Online.getUserId()===player.id?" (you)":""}
                </span>
            })}
            <div style={{marginTop:"2rem",display:"flex",justifyContent:"space-evenly",width:"100%"}}>
                <button style={
                    {
                        backgroundColor:"gray",
                        color:"white",
                        padding:"5px",
                        border:"none"
                    }
                } onClick={() => {
                    Online.leaveRoom();
                }}>Leave Room</button>
                <button style={
                    {
                        backgroundColor:"gray",
                        color:"white",
                        padding:"5px",
                        border:"none"
                    }
                } onClick={() => {
                    Online.ready(!ready);
                    setReady(prevState => !prevState);
                }}>{ready?"Unready":"Ready"}</button>
                </div>
        </div>
    </div>
  )
}
