package com.unexus.websocketserver;

import android.util.Log;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
// import org.json.JSONException;
// import org.json.JSONObject;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.Arguments;
import javax.annotation.Nullable;
import java.net.InetSocketAddress;

/**
 * Created by umsi on 27/11/2017.
 */

public class WebServer extends WebSocketServer {
    private WebSocket mSocket;
    private ReactApplicationContext mContext;
    public WebServer(InetSocketAddress inetSocketAddress, ReactApplicationContext reactContext) {
        super(inetSocketAddress);
        this.mContext = reactContext;
        this.setReuseAddr(true);
    }

    private void sendEvent(String eventName,
                           @Nullable WritableMap params) {
        this.mContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        mSocket = conn;
        WritableMap params = Arguments.createMap();
        params.putString("HostName", conn.getRemoteSocketAddress().getHostName());
        sendEvent("WS_ONOPEN", params);
        // try {
        //     String jsonString = (new JSONObject()).put("type", "onMessage")
        //             .put("data", conn.getRemoteSocketAddress().getHostName() + " entered the room")
        //             .toString();
            

        //     broadcast(jsonString);
        // } catch (JSONException e) {
        //     broadcast(e.getMessage());
        // }
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        WritableMap params = Arguments.createMap();
        params.putString("HostName", conn.getRemoteSocketAddress().getHostName());
        params.putInt("Code", code);
        params.putString("Reason", reason);
        params.putBoolean("Remote", remote);
        sendEvent("WS_ONCLOSE", params);
        // try {
        //     String jsonString = (new JSONObject()).put("type", "onMessage")
        //             .put("data", conn.getRemoteSocketAddress().getHostName() + " has left the room")
        //             .toString();

        //     broadcast(jsonString);
        // } catch (JSONException e) {
        //     broadcast(e.getMessage());
        // }
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        WritableMap params = Arguments.createMap();
        params.putString("HostName", conn.getRemoteSocketAddress().getHostName());
        params.putString("Message", message);
        sendEvent("WS_ONMESSAGE", params);
        // try {
        //     String jsonString = (new JSONObject()).put("type", "onMessage")
        //             .put("data", conn.getRemoteSocketAddress().getHostName() + ": " + message)
        //             .toString();

        //     broadcast(jsonString);
        // } catch (JSONException e) {
        //     broadcast(e.getMessage());
        // }
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        WritableMap params = Arguments.createMap();
        if (conn != null) {
            params.putString("HostName", conn.getRemoteSocketAddress().getHostName());
        }
        params.putString("Error", ex.getMessage());
        sendEvent("WS_ONERROR", params);
        // try {
        //     String jsonString = (new JSONObject()).put("type", "onError")
        //             .put("data", ex.getMessage())
        //             .toString();

        //     broadcast(jsonString);
        // } catch (JSONException e) {
        //     broadcast(e.getMessage());
        // }
    }

    @Override
    public void onStart() {
        WritableMap params = Arguments.createMap();
        params.putString("Message", "Websocket server now starting");
        sendEvent("WS_ONSTART", params);
        // try {
        //     String jsonString = (new JSONObject()).put("type", "onStart")
        //             .put("data", "Websocket server now starting...")
        //             .toString();

        //     broadcast(jsonString);
        // } catch (JSONException e) {
        //     broadcast(e.getMessage());
        // }
    }

    public void sendMessage(String message) {
        if (this.mSocket != null) {
            WritableMap params = Arguments.createMap();
            params.putString("Message", message);
            sendEvent("WS_ONSENDMESSAGE", params);
            this.mSocket.send(message);
        } else {
            sendEvent("WS_NOTCONNECTED", null);
        }
    }

}

