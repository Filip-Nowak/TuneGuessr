import React, { useRef, useState } from "react";
import Online from "./Online";
import CommandsContainer from "./components/CommandsContainer";
import SessionInfo from "./components/SessionInfo";
import RoomInfo from "./components/RoomInfo";

export default function PickPage() {
  const pick = useRef(null);
  const nickname = useRef(null);
  const [connected, setConnected] = useState(false);
  const handlePick = () => {
    console.log("pick");
    const id = pick.current.value;
    Online.join(id);
  };
  const handleJoin = () => {
    console.log("join");
    const name = nickname.current.value;
    Online.join(name, onConnect);
  };

  const onConnect = () => {
    setConnected(true);
  };

  const createUser = () => {
    console.log("createUser");
    Online.createUser();
  };
  const setId = () => {
    console.log("setId");
    Online.setId();
  };
  return (
    <div>
      <h1>PickPage</h1>
      <div style={{ display: "flex" }}>
        <CommandsContainer />
        <SessionInfo />
        <RoomInfo />
      </div>
    </div>
    // <div>
    //   <h1>Create user</h1>
    //   <div>
    //     <button onClick={createUser} style={{ backgroundColor: "gray" }}>
    //       create user
    //     </button>
    //   </div>
    //   <h1>Set id</h1>
    //   <div>
    //     <button onClick={setId} style={{ backgroundColor: "gray" }}>
    //       set id
    //     </button>
    //   </div>
    //   <h1>Join</h1>
    //   <div>
    //     nickname:
    //     <input
    //       ref={nickname}
    //       type="text"
    //       style={{ border: "1px black solid" }}
    //     />
    //     <button style={{ backgroundColor: "gray" }} onClick={handleJoin}>
    //       join
    //     </button>
    //   </div>
    //   <div>
    //     <h1>Connection status</h1>
    //     <div>
    //       status:{" "}
    //       {connected ? (
    //         <span style={{ color: "green" }}>connected</span>
    //       ) : (
    //         <span style={{ color: "red" }}>disconnected</span>
    //       )}
    //     </div>
    //   </div>
    //   <h1>PickPage</h1>
    //   <div>
    //     pick challenge id:
    //     <input ref={pick} type="number" style={{ border: "1px black solid" }} />
    //     <button style={{ backgroundColor: "gray" }} onClick={handlePick}>
    //       pick
    //     </button>
    //   </div>
    //   <div>
    //     <button
    //       onClick={() => {
    //         Online.test();
    //       }}
    //     >
    //       test
    //     </button>
    //   </div>
    // </div>
  );
}
