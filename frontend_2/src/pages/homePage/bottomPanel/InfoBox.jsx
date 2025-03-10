import React, { useEffect, useState } from "react";
import styles from "./bottomPanel.module.css";
export default function InfoBox({ number, icon, title, text, visible }) {
  const [show, setShow] = useState(false);
  useEffect(() => {
    setTimeout(() => {
      setShow(visible);
    }, 500 * number - 1);
  }, [visible]);
  return (
    <div className={styles.boxContainer + " " + (show ? styles.showBox : "")}>
      <div className={styles.icon}>{icon}</div>
      <div className={styles.number}>{number}</div>
      <div className={styles.title}>{title}</div>
      <div className={styles.text}>{text}</div>
    </div>
  );
}
