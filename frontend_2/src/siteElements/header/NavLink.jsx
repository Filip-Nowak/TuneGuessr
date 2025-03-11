import React from "react";
import styles from "./header.module.css";
import { useNavigate } from "react-router-dom";
export default function NavLink({ text, icon, link, width = "", color }) {
  const navigate = useNavigate();
  return (
    <div
      className={styles.navLink + " " + color}
      style={{ width: width }}
      onClick={() => {
        navigate(link);
      }}
    >
      <div className={styles.textContainer}>
        <div className={styles.text}>{text}</div>
      </div>
      <div className={styles.icon}>{icon}</div>
    </div>
  );
}
