import React, { useEffect, useRef, useState } from "react";
import Online from "./online/Online";
import CommandsContainer from "./components/CommandsContainer";
import SessionInfo from "./components/SessionInfo";
import RoomInfo from "./components/RoomInfo";
import { set } from "react-hook-form";
import RoomView from "./components/RoomViwe";

export default function PickPage() {
  const [connected, setConnected] = useState(false);
  const [session, setSession] = useState({});
  const [updated, setUpdated] = useState(false);
  const [room, setRoom] = useState({});
  const [players, setPlayers] = useState([]);
  const [hostId, setHostId] = useState("");
  const handleSessionChange = (session) => {
    console.log("handleSessionChange xd");
    setUpdated(true);
    setSession(session);
  };
  const handleRoomCreated = (room) => {
    console.log("handleRoomCreated xd");
    setRoom(room);
    setUpdated(false);
    Online.setRoomId(room.id);
    console.log("room created");
    console.log(room);
  };
  console.log(players);
  const handleNewPlayrJoined = (player) => {
    console.log("join room");
    // setPlayers([
    //   ...players,
    //   {
    //     nickname: player.nickname,
    //     ready: false,
    //     id: player.id,
    //   },
    // ]);
    setPlayers((prev) => [
      ...prev,
      {
        nickname: player.nickname,
        ready: false,
        id: player.id,
      },
    ]);
  };

  const handleJoinedRoom = (room) => {
    Online.setRoomId(room.id);
    setRoom(room);
    setPlayers(room.players);
    setHostId(room.hostId);
    setUpdated(false);
  };
  const handleLeftRoom = (info) => {
    const { playerId, hostId } = info;
    if (playerId === Online.getUserId()) {
      Online.setRoomId("");
      setRoom({});
      setPlayers([]);
      setHostId("");
    } else {
      setPlayers((prev) => [
        ...prev.filter((player) => player.id !== playerId),
      ]);
      setHostId(hostId);
    }
  };
  const handlePlayerReady = (info) => {
    const { playerId, ready } = info;
    if (playerId === Online.getUserId()) {
      Online.setReady(ready);
    }
    setPlayers((prev) =>
      prev.map((player) => {
        if (player.id === playerId) {
          return { ...player, ready: ready };
        }
        return player;
      })
    );
  };
  useEffect(() => {
    Online.setSessionUpdateHandler(handleSessionChange);
    Online.setCreateRoomHandler(handleRoomCreated);
    Online.setNewPlayerJoinedHandler(handleNewPlayrJoined);
    Online.setJoinedRoomHandler(handleJoinedRoom);
    Online.setPlayerLeftHandler(handleLeftRoom);
    Online.setPlayerReadyHandler(handlePlayerReady);
    Online.setGameStartedHandler(() => {
      console.log("game started");
    });
    Online.setNextSongHandler((body) => {
      console.log("next song");
      console.log(body)
    });
  }, []);
  if (!updated) {
    setSession({});
    Online.getSession();
    setUpdated(true);
    setPlayers(room.players);
    setHostId(room.hostId);
  }
  return (
    <div>
      <h1>PickPage</h1>
      <div style={{ display: "flex" }}>
        <CommandsContainer
          setConnected={setConnected}
          setUpdated={setUpdated}
        />
        <SessionInfo connected={connected} session={session} />
        <RoomInfo room={room} />
      </div>
      <RoomView players={players} hostId={hostId} />
    </div>
  );
}
