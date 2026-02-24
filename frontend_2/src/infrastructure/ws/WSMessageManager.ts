import { IMessage } from "@stomp/stompjs";
import { MessageType } from "./MessageType";
type Handler = (payload: unknown) => void;
export default class WSMessageHandler {
  private handlers: Record<string, Handler[]> = {};
  constructor() {}
  addHandler(type: MessageType, handler: Handler) {
    if (this.handlers[type]) {
      this.handlers[type].push(handler);
    } else {
      this.handlers[type] = [handler];
    }
  }
  handleMessage(message: IMessage) {
    const body = JSON.parse(message.body);
    const type = body.type;
    const msg = body.message;
    if (this.handlers[type] && this.handlers[type].length > 0) {
      this.handlers[type].forEach((h) => {
        h(msg);
      });
    }
  }
}
