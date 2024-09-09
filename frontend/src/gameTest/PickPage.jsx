import React, { useEffect, useRef, useState } from "react";
import Online from "./Online";
import CommandsContainer from "./components/CommandsContainer";
import SessionInfo from "./components/SessionInfo";
import RoomInfo from "./components/RoomInfo";

export default function PickPage() {
  const [connected, setConnected] = useState(false);
  const [session, setSession] = useState({});
  const [updated, setUpdated] = useState(false);
  const handleSessionChange = (session) => {
    console.log("handleSessionChange xd");
    setUpdated(true);
    setSession(session);
  }
  useEffect(() => {
    Online.handleSessionChange=handleSessionChange;
  },[])
  if(!updated){
    setSession({});
    Online.getSession();
    setUpdated(true);
  }
  return (
    <div>
      <h1>PickPage</h1>
      <div style={{ display: "flex" }}>
        <CommandsContainer setConnected={setConnected} setUpdated={setUpdated}/>
        <SessionInfo connected={connected} session={session}/>
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
