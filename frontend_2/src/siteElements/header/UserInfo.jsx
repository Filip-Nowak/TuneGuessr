import React, { useContext, useEffect, useState } from "react";
import styles from "./header.module.css";
import LogInBtn from "./LogInBtn";
import { getHeaderUserData } from "../../utils/http/userData";
import { UserDataContext } from "../../utils/contexts";
import { useNavigate } from "react-router-dom";
export default function UserInfo() {
  const navigate = useNavigate();
  const { userData, setUserData } = useContext(UserDataContext);
  return (
    <div className={styles.userInfo}>
      {userData === null ? (
        <>
          <LogInBtn
            text="sign in"
            onClick={() => {
              navigate("/authenticate");
            }}
          />
          <LogInBtn
            text="sign up"
            className={styles.signupBtn}
            onClick={() => {
              navigate("/authenticate?register");
            }}
          />{" "}
        </>
      ) : (
        <div className={styles.userDataContainer}>
          <i className="fas fa-user"></i>
          <div>{userData.nickname}</div>
        </div>
      )}
    </div>
  );
}
