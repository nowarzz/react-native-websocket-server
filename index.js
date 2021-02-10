import {NativeEventEmitter, NativeModules } from 'react-native';

const { RNWebsocketServer } = NativeModules;

export default class WebsocketServer {
    _isOpen;
    constructor (port = 3770) {
        this.port = port;
        this.eventEmitter = new NativeEventEmitter(RNWebsocketServer);
        this._isOpen = false;
    }
    /**
     * Starts websocket server
     */
    start () {
        RNWebsocketServer.start(this.port);
        this.isOpen = true;
    }

    /**
     * Stops/closes websocket server
     */
    stop () {
        RNWebsocketServer.stop();
        this.isOpen = false;
    }

    sendMessage (message) {
        RNWebsocketServer.sendMessage(message);
    }

    getEvent () {
        return this.eventEmitter;
    }

    isOpen () {
        return this._isOpen;
    }
    
}