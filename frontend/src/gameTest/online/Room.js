export default class Room {
  #roomId;
  #players;
  #hostId;
  #inGame;
  #mode;
  #challengeId;
  constructor(room) {
    this.#roomId = room.id;
    this.#players = room.players;
    this.#hostId = room.hostId;
    this.#mode = room.gameMode;
    this.#challengeId = room.challengeId;
    if (room.inGame !== undefined) {
      this.#inGame = room.inGame;
    } else {
      this.#inGame = false;
    }
  }
  addPlayer(player) {
    this.#players.push({ ...player, score: 0, time: 0, finished: false });
  }
  removePlayer(player) {
    for (let i = 0; i < this.#players.length; i++) {
      if (this.#players[i].id === player.playerId) {
        this.#players.splice(i, 1);
        break;
      }
    }
  }
  setPlayerReady(playerId, ready) {
    for (let i = 0; i < this.#players.length; i++) {
      if (this.#players[i].id === playerId) {
        this.#players[i].ready = ready;
        break;
      }
    }
  }
  getId() {
    return this.#roomId;
  }
  getPlayers() {
    return this.#players;
  }
  getHostId() {
    return this.#hostId;
  }
  setInGame(inGame) {
    this.#inGame = inGame;
  }
  getInGame() {
    return this.#inGame;
  }
  getMode() {
    return this.#mode;
  }
  getChallengeId() {
    return this.#challengeId;
  }
  clone() {
    const room=new Room({
      id: this.#roomId,
      players: this.#players,
      hostId: this.#hostId,
      gameMode: this.#mode,
      challengeId: this.#challengeId,
      inGame: this.#inGame,
    });
    return room;
  }
  setMode(mode){

    this.#mode = mode;
  }
  setHostId(hostId) {
    this.#hostId = hostId;
  }
  setPlayerFinished(playerId, score, time) {
    for (let i = 0; i < this.#players.length; i++) {
      if (this.#players[i].id === playerId) {
        this.#players[i].score = score;
        this.#players[i].time = time;
        this.#players[i].finished = true;
        break;
      }
    }
  }
  setChallengeId(challengeId) {
    this.#challengeId = challengeId;
  }
}
