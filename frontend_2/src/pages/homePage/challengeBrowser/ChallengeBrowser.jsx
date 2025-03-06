import React from 'react'
import { useState } from 'react';
import styles from './challengeBrowser.module.css'
import Challenge from './Challenge';
export default function ChallengeBrowser({publicChallenges=[], privateChallenges=[]}) {
    const [priv, setPriv] = useState(false);
    const handleBrowserChange = (isPrivate)=>{
        setPriv(isPrivate);
    }
    console.log(priv)
  return (
    <div className={styles.container}>
        <div className={styles.header}>Challenges</div>
        <div className={styles.typeSelector}>
            <div className={styles.typeSelectorBtn + " " + (!priv ? styles.selectedType:"")} onClick={()=>{handleBrowserChange(false)}}>public</div>
            <div className={styles.typeSelectorBtn + " " + (priv ? styles.selectedType:"")} onClick={()=>{handleBrowserChange(true)}}>private</div>
        </div>
        <div className={styles.challengeContainer}>
            <div
                className={styles.invisibleBox + " " + (priv ? styles.privateEnabled:"")}
            >
            <div className={styles.challengeBox}>
                {publicChallenges.map((challenge, index)=>{
                    return <Challenge key={index} name={challenge.name}/>
                })}
                </div>
                <div className={styles.challengeBox}>
                {privateChallenges.map((challenge, index)=>{
                    return <Challenge key={index} name={challenge.name}/>
                })}
                </div>
                </div>
    </div>
    </div>
  )
}
