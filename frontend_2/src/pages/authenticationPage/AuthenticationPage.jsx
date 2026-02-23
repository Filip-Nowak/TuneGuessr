import React, { useContext, useState } from "react";
import styles from "./authentication.module.css";
import AuthMenu from "./AuthMenu";
import { logIn } from "../../utils/http/auth";
import { useNavigate } from "react-router-dom";
import { UserDataContext } from "../../utils/contexts";
import { authService } from "../../utils/services/auth/AuthService";
import { checkHandlable } from "../../utils/services/ApiErrors";
export default function AuthenticationPage() {
  const navigate = useNavigate();
  const register =
    new URLSearchParams(window.location.search).get("register") !== null;
  const [login, setLogin] = useState(!register);
  const [animation, setAnimation] = useState(false);
  const userDataContext = useContext(UserDataContext);
  const [loginData, setLoginData] = useState({
    email: {
      type: "email",
      placeholder: "Enter email",
      key: "Email",
      value: "",
      validate: (value) => {
        return value !== "";
      },
    },
    password: {
      type: "password",
      placeholder: "Enter password",
      key: "Password",
      value: "",
      validate: (value) => {
        return value !== "";
      },
    },
  });
  const [registerData, setRegisterData] = useState({
    email: {
      type: "email",
      placeholder: "Enter email",
      key: "Email",
      value: "",
      validate: (value) => {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return emailRegex.test(value);
      },
    },
    password: {
      type: "password",
      placeholder: "Enter password",
      key: "Password",
      value: "",
      validate: (value) => {
        return value !== "" && value.length >= 8;
      },
    },
    confirmPassword: {
      type: "password",
      placeholder: "Confirm password",
      key: "Confirm Password",
      value: "",
      validate: (value, obj) => {
        let password = obj.password.value;
        return value !== "" && value.length >= 8 && value === password;
      },
    },
  });
  const handleRotate = () => {
    setAnimation(true);
    setLogin((prev) => !prev);
  };
  const handleLogin = async () => {
    const body = {
      email: loginData.email.value,
      password: loginData.password.value,
    };
    // const response = await logIn(body.email, body.password);
    try {
      await authService.authenticate(body.email, body.password);
    } catch (e) {
      checkHandlable(e);
      if (e.errors[0].status === 31) {
        alert("Invalid credentials. Please try again.");
      } else alert("An error occurred. Please try again later.");
      return;
    }
    userDataContext.setRefreshed(false);
    navigate("/");
  };

  return (
    <div className={styles.pageContainer}>
      <div className={styles.topBanner}>
        <div className={styles.home}> TuneGuessr </div>
      </div>
      <div className={styles.authenticationMenuContainer}>
        <AuthMenu
          animation={animation}
          className={""}
          submitText={"Register"}
          title={"Register"}
          formData={registerData}
          updateFormData={setRegisterData}
          visible={!login}
          setLogin={setLogin}
          swapInfo={"Already have an account? Login here!"}
          handleSwap={handleRotate}
        />
        <AuthMenu
          animation={animation}
          submitText={"Login"}
          title={"Login"}
          formData={loginData}
          updateFormData={setLoginData}
          visible={login}
          setLogin={setLogin}
          swapInfo={"Don't have an account? Register here!"}
          handleSwap={handleRotate}
          handleSubmit={handleLogin}
        />
        {/* <AuthMenu
          submitText={"Register"}
          title={"Register"}
          formData={loginData}
          updateFormData={setLoginData}
          visible={!login}
          className={styles.registerMenu}
        /> */}
      </div>
    </div>
  );
}
