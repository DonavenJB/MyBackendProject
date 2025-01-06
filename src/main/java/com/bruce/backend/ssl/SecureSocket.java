package com.bruce.backend.ssl;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.security.SecureRandom;
import java.util.Arrays;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

public class SecureSocket {
    protected SSLSocket socket = null;

    private static byte[] buffer = null;

    public static SSLSocketFactory getMyFactory(TrustEventListener armitageTrustListener) throws Exception {
        SSLContext sSLContext = SSLContext.getInstance("SSL");
        sSLContext.init(null, new TrustManager[]{new TrustEventManager(armitageTrustListener)}, new SecureRandom());
        return sSLContext.getSocketFactory();
    }

    public SecureSocket(String string, int n, TrustEventListener armitageTrustListener) throws Exception {
        SSLSocketFactory sSLSocketFactory = getMyFactory(armitageTrustListener);
        this.socket = (SSLSocket) sSLSocketFactory.createSocket(string, n);
        this.socket.setSoTimeout(4048);
        this.socket.startHandshake();
    }

    public SecureSocket(Socket socket) throws Exception {
        SSLContext sSLContext = SSLContext.getInstance("SSL");
        sSLContext.init(null, new TrustManager[]{new TrustAllTrustManager()}, new SecureRandom());
        SSLSocketFactory sSLSocketFactory = sSLContext.getSocketFactory();
        this.socket = (SSLSocket) sSLSocketFactory.createSocket(socket, socket.getInetAddress().getHostName(), socket.getPort(), true);
        this.socket.setUseClientMode(true);
        this.socket.setSoTimeout(4048);
        this.socket.startHandshake();
    }

    public Socket getSocket() {
        return this.socket;
    }
}