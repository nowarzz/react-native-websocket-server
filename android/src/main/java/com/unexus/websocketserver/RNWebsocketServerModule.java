package com.unexus.websocketserver;

import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketListener;
import org.java_websocket.drafts.Draft;
import org.java_websocket.exceptions.InvalidDataException;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.handshake.Handshakedata;
import org.java_websocket.handshake.ServerHandshake;
import org.java_websocket.handshake.ServerHandshakeBuilder;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Inet4Address;
import java.net.SocketException;
import java.util.Enumeration;
/**
 * Created by umsi on 27/11/2017.
 */

public class RNWebsocketServerModule extends ReactContextBaseJavaModule {
    private static final String TAG = "RNWebsocketServerModule";
    private WebServer webServer = null;
    private ReactApplicationContext mContext;

    public RNWebsocketServerModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.mContext = reactContext;
    }

    @Override
    public String getName() {
        return "RNWebsocketServer";
    }

    @ReactMethod
    public void start(int port) throws IOException, InterruptedException {
        InetAddress ipAddress = getInetAddress();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(ipAddress, port);

        webServer = new WebServer(inetSocketAddress, this.mContext);

        webServer.start();
    }

    @ReactMethod
    public void stop() throws IOException, InterruptedException {
        if (webServer != null) {
            webServer.stop();
        }
    }

    @ReactMethod
    public void sendMessage(String message) {
        if (webServer != null) {
            webServer.sendMessage(message);
        }
    }
    
    private static InetAddress getInetAddress() {
        try {
            for (Enumeration en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface networkInterface = (NetworkInterface) en.nextElement();

                for (Enumeration enumIpAddr = networkInterface.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = (InetAddress) enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress;
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
            Log.e(TAG, "Error getting the network interface information");
        }

        return null;
    }
}
