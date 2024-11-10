import React, { useRef, useState } from "react";
import styles from "./joinRoomStyles.module.css";
import Online from "../../gameTest/online/Online";
import { useNavigate } from "react-router-dom";
export default function CreateContent() {
  const modes = ["CLASSIC", "SURVIVAL", "RUSH", "MULTIPLAYER"];
  const [modeSelected, setModeSelected] = useState(0);
  // const [challengeId, setChallengeId] = useState(null);
  const challengeInput = useRef(null);
  const navigate = useNavigate();
  const onCreate = (data)=>{
    console.log("created room",data);
    navigate("/room");

  }
  const handleCreate = async () => {
    console.log("create room");
    const nickname = prompt("Enter your nickname");
    if (nickname && challengeInput.current.value) {
      console.log("nickname", nickname);
    }
    // await Online.createUser(nickname);
    await Online.connect(nickname,()=>{console.log("dupa")
      Online.setCreateRoomHandler(onCreate);
      Online.createRoom(challengeInput.current.value,modes[modeSelected]);

    });
   
  };
  return (
    <div className={styles.container}>
      <div style={{ display: "flex", fontSize: "1.5rem" }}>
        <div>challenge id: </div>
        <input
        ref={challengeInput}
          type="text"
          style={{
            width: "70%",
            fontSize: "1.5rem",
            border: "2px solid black",
          }}
        />
      </div>
      <div className={styles.modePicker}>
        {modes.map((mode) => (
          <div
            onClick={() => setModeSelected(modes.indexOf(mode))}
            key={mode}
            className={
              styles.mode +
              " " +
              (modeSelected === modes.indexOf(mode) ? styles.modeSelected : "")
            }
            style={{ width: "30%" }}
          >
            {mode}
          </div>
        ))}
      </div>
      <div
        style={{
          width: "30%",
          fontSize: "3rem",
          backgroundColor: "blue",
          color: "white",
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
          cursor: "pointer",
          marginTop: "4rem",
          marginLeft: "auto",
          marginRight: "auto",
        }}
        onClick={handleCreate}
      >
        create
      </div>
    </div>
  );
}
