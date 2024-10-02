import React, { useEffect, useState } from "react";
import styles from "../components/joinRoom/joinRoomStyles.module.css";
import PickButton from "../components/joinRoom/PickButton";
import JoinContent from "../components/joinRoom/JoinContent";
import CreateContent from "../components/joinRoom/CreateContent";
export default function JoinRoomPage() {
  const [join, setJoin] = useState(true);
  return (
    <div>
      <div
        style={{
          display: "flex",
          width: "50%",
          marginLeft: "auto",
          marginRight: "auto",
          marginTop: "2rem",
        }}
      >
        <PickButton
          name="Join Room"
          selected={join}
          setSelected={() => {
            setJoin(true);
          }}
        />
        <PickButton
          name="Create Room"
          selected={!join}
          setSelected={() => {
            setJoin(false);
          }}
        />
      </div>

      {join ? <JoinContent /> : <CreateContent />}
    </div>
  );
}
