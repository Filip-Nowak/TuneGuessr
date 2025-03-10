import React, { useEffect, useState } from "react";
import styles from "./homePage.module.css";
import HomeMenuBox from "./HomeMenuBox";
import ChallengeBrowser from "./challengeBrowser/ChallengeBrowser";
import { getPopularChallenges } from "../../utils/http/publicData";
import UserPanel from "./userPanel/UserPanel";
import BottomPanel from "./bottomPanel/BottomPanel";
export default function HomePage() {
  const [welcome, setWelcome] = useState(true);
  const [publicChallenges, setPublicChallenges] = useState([]);
  const [privateChallenges, setPrivateChallenges] = useState([]);
  useEffect(() => {
    getChallenges();
  }, []);
  const getChallenges = async () => {
    const challenges = await getPopularChallenges();
    console.log(challenges);
    const xd = [
      {
        name: "challenge1",
      },
      {
        name: "challenge2",
      },
      {
        name: "challenge3",
      },
    ];
    setPublicChallenges(challenges.data);
    const privateChallenges = challenges.data;
    setPrivateChallenges(privateChallenges);
    setPublicChallenges([
      ...challenges.data,
      ...challenges.data,
      ...challenges.data,
      ...challenges.data,
    ]);
  };
  return (
    <div className={styles.homePage}>
      <div
        className={
          styles.invisibleBlock +
          " " +
          (welcome ? "" : styles.hideInvisibleBlock)
        }
      ></div>
      <div
        div
        className={
          styles.homePageBackground +
          " " +
          (welcome ? "" : styles.showBackground)
        }
      ></div>
      <div
        className={
          styles.startContainer +
          " " +
          (welcome ? "" : styles.hideStartContainer)
        }
      >
        <div className={styles.title + " " + (welcome ? "" : styles.hideTitle)}>
          TuneGuessr
        </div>
        <div
          className={
            styles.subtitle + " " + (welcome ? "" : styles.hideSubtitle)
          }
        >
          The Best way to test your music knowledge
        </div>
        <div
          className={
            styles.startButton + " " + (welcome ? "" : styles.hideStartButton)
          }
          onClick={() => {
            setWelcome(false);
            console.log("xd");
            console.log(welcome);
          }}
        >
          start
        </div>
      </div>
      <div
        className={
          styles.homePageContainer +
          " " +
          (welcome ? "" : styles.showHomePageContainer)
        }
      >
        <div className={styles.mainPanel}>
          <HomeMenuBox
            className={styles.challengeBrowserPadding}
            visible={!welcome}
            displayDelay={1}
          >
            <ChallengeBrowser
              publicChallenges={publicChallenges}
              privateChallenges={privateChallenges}
            />
          </HomeMenuBox>
          <div className={styles.centerBox}>
            <div className={styles.playButtonContainer}>
              <div
                className={
                  styles.playButton +
                  " " +
                  (welcome ? "" : styles.showPlayButton)
                }
              >
                <div className={styles.blueBackground}></div>
                <div className={styles.playTitle}>PLAY</div>
                <div className={styles.goldBackground}></div>
              </div>
            </div>
            <HomeMenuBox
              className={styles.challengesButton}
              visible={!welcome}
              displayDelay={4}
            >
              <i class="fa-solid fa-magnifying-glass"></i>
              <span>challenges</span>
            </HomeMenuBox>
          </div>
          <UserPanel visible={!welcome} />
        </div>
        <BottomPanel show={!welcome} />
      </div>
    </div>
  );
}
