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
    this.#mode = room.mode;
    this.#challengeId = room.challengeId;
    if (room.inGame !== undefined) {
      this.#inGame = room.inGame;
    } else {
      this.#inGame = false;
    }
  }
  addPlayer(player) {
    this.#players.push(player);
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
    return new Room({
      id: this.#roomId,
      players: this.#players,
      hostId: this.#hostId,
      mode: this.#mode,
      challengeId: this.#challengeId,
      inGame: this.#inGame,
    });
  }
  setHostId(hostId) {
    this.#hostId = hostId;
  }
}
