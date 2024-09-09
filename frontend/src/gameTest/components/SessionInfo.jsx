import React from "react";

export default function SessionInfo({connected,session}) {
  console.log(session)
  return (
    <div style={{ width: "100%", border: "5px black dashed" }}>SessionInfo
    <div>
      <div>
        status:{" "}
        {connected ? (
          <span style={{ color: "green" }}>connected</span>
        ) : (
          <span style={{ color: "red" }}>disconnected</span>
        )}
      </div>
      <div>
        {
          Object.keys(session).length===0?"no session"
          :
          <div>
            {
              Object.keys(session).map((key,index)=>{
                return <div key={index
                }>
                  {key}: {session[key]}
                </div>}
              )}
            
          </div>
        }
      </div>
    </div>
    </div>
  );
}
