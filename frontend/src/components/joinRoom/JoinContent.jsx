import React from "react";
import styles from "./joinRoomStyles.module.css";
export default function JoinContent() {
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
        >
          join
        </div>
      </div>
    </div>
  );
}
