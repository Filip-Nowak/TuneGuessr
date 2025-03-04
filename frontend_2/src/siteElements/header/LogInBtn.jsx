import React from 'react'
import styles from './header.module.css'
export default function LogInBtn({text,onClick=()=>{},className}) {
  return (
    <div className={styles.loginBtn + " " + className} onClick={onClick}>
        {text}
    </div>
  )
}
