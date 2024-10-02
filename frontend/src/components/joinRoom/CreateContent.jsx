import React, { useState } from "react";
import styles from "./joinRoomStyles.module.css";
export default function CreateContent() {
  const modes = ["CLASSIC", "SURVIVAL", "RUSH", "MULTIPLAYER"];
  const [modeSelected, setModeSelected] = useState(0);
  return (
    <div className={styles.container}>
      <div style={{ display: "flex", fontSize: "1.5rem" }}>
        <div>challenge id: </div>
        <input
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
      >
        create
      </div>
    </div>
  );
}
