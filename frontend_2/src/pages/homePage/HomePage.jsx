import React, { useState } from 'react'
import styles from './homePage.module.css'
export default function HomePage() {
  const [welcome, setWelcome] = useState(true);
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

  
    </div>
  )
}
