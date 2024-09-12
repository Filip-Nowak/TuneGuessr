export default class MessageHandler {
  handlers = {};
  addHandler(type, handler) {
    this.handlers[type] = handler;
  }
  handle(type, message) {
    const handler = this.handlers[type];
    if (handler) {
      handler(message);
    } else {
      throw new Error("No handler for message type: " + type);
    }
  }
  removeHandler(type) {
    delete this.handlers[type];
  }
}
