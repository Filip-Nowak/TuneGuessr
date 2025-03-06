import React from 'react'
import styles from './homePage.module.css'
export default function HomeMenuBox({children}) {
  return (
    <div className={styles.homeMenuBox}>
        {children}
    </div>
  )
}
