import React from "react";
import styles from "./header.module.css";
export default function ChallengeSearch() {
  return (
    <div className={styles.searchContainer}>
      <input
        type="text"
        className={styles.searchInput}
        placeholder="Search challenges"
      />
      <div className={styles.searchIcon}>
        <i className="fas fa-search"></i>
      </div>
    </div>
  );
}
