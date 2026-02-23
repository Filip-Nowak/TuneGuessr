export default class MessageHandler {
  handlers = {};
  addHandler(type, handler,removable=true) {
    if(this.handlers[type]) {
      this.handlers[type].push({handler:handler,removable:removable});
    }else {
      this.handlers[type] = [{handler:handler,removable:removable}];
    }
  }
  handle(type, message) {
    const handlers = this.handlers[type];
    if(!handlers) {
      throw new Error("No handler for message type: " + type
      );
    }
    if (handlers.length!==0) {
      for(let handler of handlers) {
        handler.handler(message);
      }
    } else {
      throw new Error("No handler for message type: " + type);
    }
  }
  removeHandler(type) {
    const handlers = this.handlers[type];
    if(handlers.length>0){
      for(let handler in handlers){
          if(handler.removable){
            handlers.splice(handlers.indexOf(handler),1);
          }
      }
    }
  }
  clearHandlers() {
    for(let type in this.handlers){
      for(let handler in this.handlers[type]){
        if(handler.removable){
          this.handlers[type].splice(this.handlers[type].indexOf(handler),1);
        }
      }
    }
  }
}
