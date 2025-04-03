import React from "react";
import styles from "./header.module.css";
import LogInBtn from "./LogInBtn";
import NavLink from "./NavLink";
import ChallengeSearch from "./ChallengeSearch";
import UserInfo from "./UserInfo";
export default function Header() {
  return (
    <div>
      <div className={styles.fakeHeader}></div>
      <div className={styles.headerContainer}>
        <div className={styles.header}>
          <div className={styles.logoContainer}>
            <div className={styles.logo}>TGSR</div>
          </div>
          <div className={styles.navLinks}>
            <NavLink
              color={styles.blueNavLink}
              text="home"
              link="/"
              icon={<i className="fas fa-home"></i>}
            />

            <NavLink
              color={styles.redNavLink}
              text="challenges"
              link="/challenges"
              icon={<i className="fas fa-music"></i>}
            />
            <ChallengeSearch />

            <NavLink
              color={styles.purpleNavLink}
              width="7vw"
              text="about"
              link="/about"
              icon={<i className="fas fa-info-circle"></i>}
            />
          </div>
          <UserInfo />
        </div>
        <div className={styles.movingBorder}></div>
      </div>
    </div>
  );
}
