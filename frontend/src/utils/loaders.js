import Online from "../gameTest/online/Online";

export function loadRoom() {
  if(Online.getRoom()){
    return {
        room: Online.getRoom(),
    }
  }
  throw new Error("not in room");
}