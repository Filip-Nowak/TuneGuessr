import React, { useEffect, useState } from 'react'
import styles from './homePage.module.css'
import HomeMenuBox from './HomeMenuBox';
import ChallengeBrowser from './challengeBrowser/ChallengeBrowser';
import { getPopularChallenges } from '../../utils/http/publicData';
export default function HomePage() {
  const [welcome, setWelcome] = useState(false);
  const [publicChallenges, setPublicChallenges] = useState([]);
  const [privateChallenges, setPrivateChallenges] = useState([]);
  useEffect(()=>{
    getChallenges();
    
  },[])
  const getChallenges = async ()=>{
    const challenges = await getPopularChallenges();
    console.log(challenges);
    const xd = [
      {
        name:"challenge1",
      },
      {
        name:"challenge2",
      },
      {
        name:"challenge3",
      }
    ]
    setPublicChallenges(challenges.data);
    const privateChallenges = challenges.data;
    setPrivateChallenges(privateChallenges);
    setPublicChallenges(
      [
        ...challenges.data,
        ...challenges.data,
        ...challenges.data,
        ...challenges.data,
      ]
    );

  }
  return (
    <div>
    <div div className={styles.homePageBackground + " " + (welcome ? "":styles.showBackground)}></div>
        <div className={styles.startContainer+ " " + (welcome ? "":styles.hideStartContainer)}>
          <div className={styles.title + " " + (welcome ? "":styles.hideTitle)}>TuneGuessr</div>
          <div className={styles.subtitle+ " " + (welcome ? "":styles.hideSubtitle)}>The
            Best way to test your music knowledge
          </div>
          <div className={styles.startButton + " " + (welcome ? "":styles.hideStartButton)} onClick={()=>{
            setWelcome(false)
            console.log("xd")
            console.log(welcome)
            }}>
            start
          </div>
          </div>
          <div className={styles.homePageContainer}>
            <div className={styles.mainPanel}>
              <HomeMenuBox>
                <ChallengeBrowser 
                publicChallenges={publicChallenges}
                privateChallenges={privateChallenges}
                />
              </HomeMenuBox>
              <div className={styles.centerBox}>
                <div className={styles.playButton}>
                  PLAY
                </div>
                <div className={styles.searchChallenges}>
                  challenges
                  </div>
              </div>
              <div className={styles.userPanel}>
                user
                </div>
            </div>
          </div>

  
    </div>
  )
}
