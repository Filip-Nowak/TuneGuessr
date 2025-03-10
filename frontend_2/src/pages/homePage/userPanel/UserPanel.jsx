import React from "react";
import styles from "./userPanel.module.css";
import HomeMenuBox from "../HomeMenuBox";
export default function UserPanel({ loggedIn, visible }) {
  return (
    <div className={styles.userPanel}>
      <div className={styles.userInfoContainer}>
        <HomeMenuBox
          className={styles.userInfo}
          visible={visible}
          displayDelay={2}
        >
          <div className={styles.userImage}></div>
          <div className={styles.info}>
            <div className={styles.username}>
              <div>cwel</div>
            </div>
            <div className={styles.goToProfile}>got see profile</div>
          </div>
        </HomeMenuBox>
      </div>
      <HomeMenuBox
        className={styles.friendsContainer}
        visible={visible}
        displayDelay={3}
      >
        <div className={styles.friendsTitle}>Friends</div>
        {loggedIn ? (
          <div className={styles.friends}>
            <div className={styles.friend}>friend1</div>
            <div className={styles.friend}>friend2</div>
            <div className={styles.friend}>friend3</div>
          </div>
        ) : (
          <div className={styles.notLoggedIn}>
            <div>
              {" "}
              <span
                onClick={() => {
                  console.log("xd");
                }}
              >
                Log in{" "}
              </span>
              to see your friends
            </div>
          </div>
        )}
      </HomeMenuBox>
    </div>
  );
}
