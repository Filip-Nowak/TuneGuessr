import React, { useEffect, useState } from "react";
import styles from "./homePage.module.scss";
export default function HomeMenuBox({
  children,
  className = "",
  style,
  displayDelay,
  visible,
}) {
  const [show, setShow] = useState();
  useEffect(() => {
    setTimeout(
      () => {
        setShow(visible);
      },
      500 + displayDelay * 200,
    );
  }, [visible]);
  return (
    <div
      style={style}
      className={
        styles.homeMenuBox +
        " " +
        className +
        " " +
        (show ? styles.showBox : "")
      }
    >
      {children}
    </div>
  );
}
