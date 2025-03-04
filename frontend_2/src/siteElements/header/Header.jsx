import React from 'react'
import styles from './header.module.css'
import LogInBtn from './LogInBtn'
export default function Header() {
  return (
    <div>
    <div className={styles.headerContainer}>
      <div className={styles.logo}>TGSR</div>
      <div className={styles.userInfo}>
        <LogInBtn text="sign in"/>
        <LogInBtn text="sign up" className={styles.signupBtn}/>
      </div>
    </div>
    <div className={styles.movingBorder}></div>
    </div>
  )
}
