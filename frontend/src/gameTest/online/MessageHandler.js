export default class MessageHandler {
  handlers = {};
  addHandler(type, handler) {
    if(this.handlers[type]) {
      this.handlers[type].push(handler);
    }else {
      this.handlers[type] = [handler];
    }
  }
  handle(type, message) {
    const handlers = this.handlers[type];
    if (handlers.length!==0) {
      for(let handler of handlers) {
        handler(message);
      }
    } else {
      throw new Error("No handler for message type: " + type);
    }
  }
  removeHandler(type, handler) {
    const handlers = this.handlers[type];
    if (handlers.length!==0) {
      const index = handlers.indexOf(handler);
      if (index !== -1) {
        handlers.splice(index, 1);
      }
    } else {
      throw new Error("No handler for message type: " + type);
    }
  }
}
