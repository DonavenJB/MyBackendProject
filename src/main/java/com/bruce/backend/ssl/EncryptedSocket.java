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

public class EncryptedSocket {
    protected SSLSocket socket = null;

    private static byte[] buffer = null;

    public static SSLSocketFactory getMyFactory(TrustEventListener secureTrustListener) throws Exception {
        SSLContext sSLContext = SSLContext.getInstance("SSL");
        sSLContext.init(null, new TrustManager[]{new TrustEventManager(secureTrustListener)}, new SecureRandom());
        return sSLContext.getSocketFactory();
    }

    public EncryptedSocket(String string, int n, TrustEventListener secureTrustListener) throws Exception {
        SSLSocketFactory sSLSocketFactory = getMyFactory(secureTrustListener);
        this.socket = (SSLSocket) sSLSocketFactory.createSocket(string, n);
        this.socket.setSoTimeout(4048);
        this.socket.startHandshake();
    }

    public EncryptedSocket(Socket socket) throws Exception {
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