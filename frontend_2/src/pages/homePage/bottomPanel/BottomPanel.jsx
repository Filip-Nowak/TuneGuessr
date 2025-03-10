import React, { useEffect, useRef, useState } from "react";
import styles from "./bottomPanel.module.css";
import InfoBox from "./InfoBox";
export default function BottomPanel({ show }) {
  const elementRef = useRef(null);
  const [isVisible, setIsVisible] = useState(false);

  useEffect(() => {
    const observer = new IntersectionObserver(
      ([entry]) => {
        if (entry.isIntersecting) {
          setIsVisible(true);
        }
      },
      { threshold: 0.2 }
    );

    if (elementRef.current) {
      observer.observe(elementRef.current);
    }

    return () => {
      if (elementRef.current) {
        observer.unobserve(elementRef.current);
      }
    };
  }, []);
  return (
    <div
      className={styles.container}
      id="bottomPanel"
      style={{ display: show ? "flex" : "none" }}
    >
      <div
        className={styles.scrollInfo}
        onClick={() => {
          window.scroll({ top: "1000", left: 0, behavior: "smooth" });
        }}
      >
        <i class="fa-solid fa-down-long"></i> how to play
        <i class="fa-solid fa-down-long"></i>
      </div>
      <div className={styles.infoContainer} ref={elementRef}>
        <InfoBox
          visible={isVisible}
          number="1"
          title={"Find or create a challenge"}
          icon={<i class="fa-solid fa-search"></i>}
          text={
            "Search for a challenge or create your own. Challenge is a set of songs that you need to guess. "
          }
        />
        <InfoBox
          visible={isVisible}
          number="2"
          title={"Select game mode"}
          icon={<i class="fa-solid fa-gamepad"></i>}
          text={
            "Choose between 3 game modes: classic, time attack or survival. You can also play with friends."
          }
        />
        <InfoBox
          visible={isVisible}
          number="3"
          title={"Play and enjoy"}
          icon={<i class="fa-solid fa-music"></i>}
          text={
            "Listen to the music and try to guess the song. The faster you guess, the more points you get."
          }
        />
      </div>

      <div
        className={styles.seeMore}
        onClick={() => {
          window.scroll({ top: "1000", left: 0, behavior: "smooth" });
        }}
      >
        see more
      </div>
    </div>
  );
}
