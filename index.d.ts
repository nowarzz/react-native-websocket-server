declare module 'react-native-websocket-server' {
  import {NativeEventEmitter} from 'react-native';
  export default class WebsocketServer{
    port: number;
    eventEmitter: NativeEventEmitter;
    _isOpen: boolean;

    constructor(port?: number);
    start: () => void;
    stop: () => void;
    sendMessage: (message: string) => void;
    getEvent: () => NativeEventEmitter;
    isOpen: () => boolean;
  }
}