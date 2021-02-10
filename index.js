import {NativeEventEmitter, NativeModules } from 'react-native';

const { RNWebsocketServer } = NativeModules;

export default class WebsocketServer {
    constructor (port = 3770) {
        this.port = port;
        this.eventEmitter = new NativeEventEmitter(RNWebsocketServer);
    }
    /**
     * Starts websocket server
     */
    start () {
        RNWebsocketServer.start(this.port);
    }

    /**
     * Stops/closes websocket server
     */
    stop () {
        RNWebsocketServer.stop();
    }

    sendMessage (message) {
        RNWebsocketServer.sendMessage(message);
    }

    getEvent () {
        return this.eventEmitter;
    }

    isOpen () {
        return RNWebsocketServer.isOpen();
    }
    
}