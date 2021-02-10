declare module 'react-native-websocket-server' {
  import {NativeEventEmitter} from 'react-native';
  export default class WebsocketServer{
    port: number;
    eventEmitter: NativeEventEmitter;

    constructor(port?: number);
    start: () => void;
    stop: () => void;
    sendMessage: (message: string) => void;
    getEvent: () => NativeEventEmitter;
    isOpen: () => boolean;
  }
}