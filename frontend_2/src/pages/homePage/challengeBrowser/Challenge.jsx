import React from 'react'
import styles from './challengeBrowser.module.css';
export default function Challenge({name}) {
  return (
    <div className={styles.challenge}>
        <div className={styles.img}></div>
        <div className={styles.challengeInfo}>
            <div className={styles.challengeName}
              style={{
                fontSize: name.length > 25 ? "1rem" : "1.3rem"
              }}
            >
              {name}
            </div>
            <div className={styles.challengeCreator}>
              <div>by: user</div>
              <div className={styles.amountOfSongs}>20 songs</div>
              </div>
        </div>
    </div>
  )
}
