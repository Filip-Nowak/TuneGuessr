import React, { useRef, useState } from 'react'
import Online from "../../gameTest/online/Online";
import styles from "./roomStyles.module.css";
import { useEffect } from 'react';
import { set } from 'react-hook-form';
import { useNavigate } from 'react-router-dom';

export default function RoomContent({setInRoom,setInGame,room,setRoom}) {
    const [challengeName, setChallengeName] = useState("");
    const [ready, setReady] = useState(false);
    const [showOptions, setShowOptions] = useState(false);
    const challangeModeInput = useRef(null);
    const challengeIdInput = useRef(null);
    const navigate = useNavigate();
    useEffect(() => {
        fetch("https://localhost:8080/api/challenge/"+room.getChallengeId())
        .then(response => response.json())
        .then(data => {
            setChallengeName(data.data.name);
        })
        Online.setPlayerReadyHandler(onReady);
        Online.setPlayerLeftHandler(onLeave);
        Online.setNewPlayerJoinedHandler(onJoin);
        Online.setRoomChangeHandler(onRoomChange);
        Online.setGameStartedHandler(onStart);

    }, []);
    const handleReady = () => {
        setReady(prevState => {
            Online.ready(!prevState);
            return !prevState
        });
    }
    const handleLeave = () => {
        Online.leaveRoom();
    }
    const handleStart = () => {
        Online.startGame();
    }
    const onReady = (data) => {
        setRoom(Online.getRoom().clone());
    }
    const onLeave = (data) => {
        if(Online.getUserId()===data.playerId){
            Online.disconnect();
            navigate("/");
        }else{
            setRoom(Online.getRoom().clone());
        }
    }
    const onJoin = (data) => {
        setRoom(Online.getRoom().clone());

    }
    const onRoomChange = (data) => {
        setRoom(Online.getRoom().clone());
        fetch("https://localhost:8080/api/challenge/"+room.getChallengeId())
        .then(response => response.json())
        .then(data => {
            setChallengeName(data.data.name);
        })
    }
    const onStart=()=>{
        setInGame(true);
    }
    const openOptions = () => {
        setShowOptions(true);
    }
    const changeMode = () => {
        Online.changeMode(challangeModeInput.current.value);
    }
    const changeChallenge = () => {
        Online.changeChallenge(challengeIdInput.current.value);
    }
    const allReady = room.getPlayers().filter(player => !player.ready).length===0;
  return (
    <div>
        <div style={{display:"flex", justifyContent:'center',alignItems:"center" }}><div style={{width:"5rem"}}></div><div className={styles.title} >{challengeName}</div> {room.getHostId()===Online.getUserId()?<i style={{textAlign:"center",fontSize:"2rem",width:"5rem"}} className="fa-solid fa-gear" onClick={openOptions}></i>:<div style={{width:"5rem"}}></div>}</div>
        <div className={styles.mode}>mode: {room.getMode()}</div>
        <div className={styles.codeContainer}>code:<span className={styles.code}>
            {room.getId()}
            </span></div>
            <div style={{textAlign:"center",fontSize:"0.8rem"}}>invite via link: <br/><code>https://localhost:5173/loadroom/join/{room.getId()}</code></div>
            <div className={styles.border}>
            <div className={styles.playerCount}><i className="fa-solid fa-user"></i>{room.getPlayers().length}/10</div>
        <div className={styles.playersContainer}>
                {room.getPlayers().map(player => {
                    return <div className={styles.playerContainer}>
                        {Online.getRoom().getHostId()===player.id?<i className={"fa-solid fa-crown " +  styles.crown}></i>:<div className={styles.crown}></div>}
                        <div className={styles.player} style={{
                    }}><i className="fa-regular fa-user"></i>{player.nickname}
                    </div>
                    <div className={styles.ready} style={{
                        color:player.ready?"green":""
                    }}>{player.ready?"ready":""}</div>
                    </div>

                })}
        
    </div></div>
    <div className={styles.buttonsContainer}>

        <button className={styles.button} onClick={handleLeave}>
            Leave Room
        </button>
            <button className={styles.button} onClick={handleReady}>
                {ready?"unready":"ready"}
            </button>
            {
                room.getHostId()===Online.getUserId()?
                    <button 
                        className={styles.button + " "+ 
                            (allReady?"":"disabled")
                        }
                         disabled={!allReady} onClick={handleStart}       
                        >
                Start Game
            </button>:""}

    </div>
    {
        showOptions?
        <div className={styles.challengeOptions}>
            <div className={styles.optionsHeader}>Options</div>
            <div className={styles.optionoContainer}>
                <div className={styles.optionName}>Change Challenge
                </div>
                <div className={styles.optionContent}>
                    <div>id: <input ref={challengeIdInput} defaultValue={Online.getRoom().getChallengeId()} type="text"/></div><button onClick={changeChallenge}>change</button>
                    </div>
                </div>
                <div className={styles.optionContainer}>
                    <div className={styles.optionName} >Change Mode</div>
                    <div className={styles.optionContent}>
                        mode:
                            <select
                                style={{ width: "100%", color: "black" }}
                                ref={challangeModeInput}
                                defaultValue={Online.getRoom().getMode().toUpperCase()}
                                onChange={changeMode}
                            >
                                <option value="CLASSIC">Classic</option>
                                <option value="SURVIVAL">Survival</option>
                                <option value="MULTIPLAYER">Multiplayer</option>
                        </select>
                        </div>
                    </div>
            <button className={styles.closeBtn} onClick={()=>{
                setShowOptions(false);
            }}>Close</button>
        </div>:""
    }
    </div>  
  )
}
