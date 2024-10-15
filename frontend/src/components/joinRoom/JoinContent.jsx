import React, { useRef } from "react";
import styles from "./joinRoomStyles.module.css";
import Online from "../../gameTest/online/Online";
import { useNavigate } from "react-router-dom";
export default function JoinContent() {
  const codeInput = useRef(null);
  const navigate = useNavigate();
  const handeJoin = async() => {
    const nickname = prompt("Enter your nickname");
    if (nickname && codeInput.current.value) {
      console.log("nickname", nickname);
    }
    await Online.connect(nickname,()=>{console.log("dupa")
      Online.setJoinedRoomHandler(onJoin);
      Online.joinRoom(codeInput.current.value);

    });
  }
  const onJoin = (data)=>{
    console.log("joined room",data);
    navigate("/room");
  }
  return (
    <div className={styles.container}>
      <div
        style={{
          fontSize: "3rem",
          textAlign: "center",
        }}
      >
        enter room id
      </div>
      <div style={{ display: "flex", justifyContent: "space-evenly" }}>
        <input
        ref={codeInput}
          style={{
            width: "70%",
            height: "3rem",
            fontSize: "1.5rem",
            border: "2px solid black",
          }}
          type="text"
        />
        <div
          style={{
            width: "15%",
            backgroundColor: "blue",
            color: "white",
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
            cursor: "pointer",
          }}
          onClick={
            handeJoin
          }
        >
          join
        </div>
      </div>
    </div>
  );
}
