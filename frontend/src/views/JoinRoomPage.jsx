import React, { useEffect, useState } from "react";
import styles from "../components/joinRoom/joinRoomStyles.module.css";
import PickButton from "../components/joinRoom/PickButton";
import JoinContent from "../components/joinRoom/JoinContent";
import CreateContent from "../components/joinRoom/CreateContent";
import { Outlet, useNavigate } from "react-router-dom";
export default function JoinRoomPage() {
  // const [join, setJoin] = useState(true);
  const navigate = useNavigate();
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
          selected={
            window.location.pathname === "/loadroom/join" 
          }
          setSelected={() => {
            navigate("/loadroom/join"); 
            // setJoin(true);
          }}
        />
        <PickButton
          name="Create Room"
          selected={
            window.location.pathname === "/loadroom/create"
          }
          setSelected={() => {
            navigate("/loadroom/create");
            // setJoin(false);
          }}
        />
      </div>

      {/* {join ? <JoinContent /> : <CreateContent />} */}
      <Outlet />
    </div>
  );
}
