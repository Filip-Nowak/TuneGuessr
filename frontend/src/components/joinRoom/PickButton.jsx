import React from "react";
import styles from "./joinRoomStyles.module.css";
export default function PickButton({ name, selected, setSelected }) {
  return (
    <div
      className={styles.pickButton + " " + (selected ? styles.selected : "")}
      onClick={() => setSelected()}
    >
      {name}
    </div>
  );
}
