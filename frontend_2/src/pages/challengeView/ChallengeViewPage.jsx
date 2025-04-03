import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import styles from "./challengeView.module.css";
import { set } from "react-hook-form";
import { getChallengeById } from "../../utils/http/publicData";
import PlayButton from "../../siteElements/playButton/PlayButton";
import HomeMenuBox from "../homePage/HomeMenuBox";
export default function ChallengeViewPage() {
  const { id } = useParams();
  const [challenge, setChallenge] = useState({});
  const [showSongs, setShowSongs] = useState(false);
  useEffect(() => {
    getData();
  }, []);
  const getData = async () => {
    const data = await getChallengeById(id);
    setChallenge(data);
    console.log(data);
  };
  console.log(id);
  return (
    <div className={styles.container}>
      <div className={styles.backgroundContainer}>
        <div div className={styles.background}></div>
      </div>
      <div className={styles.topPanel}>
        <div className={styles.challengeInfo}>
          <div className={styles.image}>img</div>
          <div className={styles.data}>
            <div className={styles.challengeTitle}>{challenge.name}</div>
            <div className={styles.challengeAuthor}>
              by <span>{challenge.author}</span>
            </div>
            <div className={styles.description}>{challenge.description}</div>
            <div className={styles.length}>34 songs</div>
          </div>
        </div>
        {/* <PlayButton style={{ width: "100%", height: "8vh" }} /> */}

        <div className={styles.buttonPanel}>
          <div className={styles.topButtons}>
            <div className={styles.shareButton}>
              <i className="fas fa-share"></i>
            </div>
            <div className={styles.favoriteButton}>
              <i className="fas fa-star"></i> <span>45</span>
            </div>
          </div>
          <div className={styles.bottomButtons}>
            <PlayButton style={{ width: "100%", height: "8vh" }} />
          </div>
        </div>
      </div>

      <div className={styles.bottomPanel}>
        <div className={styles.challengesContainer}>
          <HomeMenuBox visible={true}>
            <div className={styles.challenges}>
              <div className={styles.otherChallengesTitle}>
                Other Challenges by <span>{challenge.author}</span>
              </div>
            </div>
          </HomeMenuBox>
        </div>
        <div className={styles.songs}>
          <div className={styles.songsTitle}>Songs</div>

          {showSongs ? (
            <div className={styles.songsList}>
              {challenge.songs.map((song, index) => (
                <div className={styles.song} key={index}>
                  <div className={styles.songNumber}>{index + 1}</div>
                  <div className={styles.songTitle}>{song.title}</div>
                  <div className={styles.songAuthor}>{song.artist}</div>
                  <div
                    className={styles.goToYoutube}
                    onClick={() => window.open(song.url)}
                  >
                    <div className={styles.iconBackground}></div>
                    <i className="fab fa-youtube"></i>
                  </div>
                </div>
              ))}
            </div>
          ) : (
            <div
              className={styles.showSongsButton}
              onClick={() => setShowSongs(true)}
            >
              Show Songss
            </div>
          )}
        </div>
      </div>
    </div>
  );
}
