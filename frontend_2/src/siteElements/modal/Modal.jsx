import { useState } from "react";
import styles from "./modal.module.scss";
function Modal({ children, visible = false, setVisible = () => {} }) {
  return (
    <div className={styles.modal}>
      <div
        className={styles.unclickableBg + " " + (visible ? styles.visible : "")}
        onClick={() => {
          setVisible(false);
        }}
        // style={{ display: visible ? "block" : "none" }}
      ></div>
      <div
        className={styles.modalAlign + " " + (visible ? styles.visible : "")}
      >
        <div className={styles.modalContainer}>{children}</div>
      </div>
    </div>
  );
}

export default Modal;
