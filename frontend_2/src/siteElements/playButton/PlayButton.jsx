import React from "react";
import styles from "./playButton.module.css";
export default function PlayButton({ style, className = "" }) {
  return (
    <div className={styles.playButton + " " + className} style={style}>
      <div className={styles.blueBackground}></div>
      <div className={styles.playTitle}>PLAY</div>
      <div className={styles.goldBackground}></div>
    </div>
  );
}
