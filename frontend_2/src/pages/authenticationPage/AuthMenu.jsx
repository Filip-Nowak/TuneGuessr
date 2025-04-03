import React from "react";
import styles from "./authentication.module.css";
export default function AuthMenu({
  title,
  formData,
  updateFormData,
  submitText,
  className = "",
  visible,
  setLogin,
  animation = false,
  swapInfo,
  handleSwap,
  handleSubmit,
}) {
  console.log(Object.keys(formData).length);
  const handleSubmitClick = () => {
    console.log(formData);
    const formElements = Object.entries(formData);
    for (const [key, body] of formElements) {
      if (!body.validate(body.value, formData)) {
        console.log("Invalid input in " + key);
        body.error = true;
        updateFormData({ ...formData, [key]: body });
        return;
      }
    }
    handleSubmit();
  };
  return (
    <div
      className={
        styles.authMenu +
        " " +
        className +
        " " +
        (visible
          ? animation
            ? styles.visibleAnimation
            : styles.visible
          : animation
          ? styles.hiddenAnimation
          : styles.hidden)
      }
    >
      <div className={styles.menuTitle}>{title}</div>

      <div
        className={
          styles.formContainer +
          " " +
          (Object.keys(formData).length > 2 ? styles.smallerFont : "xd")
        }
      >
        {Object.entries(formData).map(([key, body]) => {
          return (
            <div className={styles.formItem} key={key}>
              <label>
                <div className={styles.formKey}>{body.key}</div>
                <input
                  className={
                    styles.formInput + (body.error ? " " + styles.error : "")
                  }
                  type={body.type}
                  placeholder={body.placeholder}
                  value={body.value}
                  onChange={(e) => {
                    updateFormData({
                      ...formData,
                      [key]: { ...body, value: e.target.value, error: false },
                    });
                  }}
                />
              </label>
            </div>
          );
        })}
      </div>
      <div style={{ width: "100%" }}>
        <div
          className={styles.alreadyInfo}
          // onClick={() => setLogin((prev) => !prev)}      other nice animation
          onClick={handleSwap}
        >
          {swapInfo}
        </div>
        <div className={styles.submitButton} onClick={handleSubmitClick}>
          {submitText}
        </div>
      </div>
    </div>
  );
}
