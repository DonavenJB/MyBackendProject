package com.bruce.backend.common;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ObjectSocketConnection {

    protected Socket client;
    protected OutputStream bout = null;
    protected boolean connected = true;

    public ObjectSocketConnection(Socket socket) throws Exception {
        this.client = socket;
        socket.setSoTimeout(0);
    }

    public boolean isConnected() {
        return this.connected;
    }

    public void writeObject(Object object) {
        if (!isConnected()) {
            return;
        }
        try {
            synchronized (this.client) {
                if (this.bout == null) {
                    this.bout = new BufferedOutputStream(this.client.getOutputStream(), 262144);
                }
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(this.bout);
                objectOutputStream.writeUnshared(object);
                objectOutputStream.flush();
            }
        } catch (IOException e) {
            close();
        }
    }

    public void close() {
        if (!isConnected()) {
            return;
        }
        try {
            this.connected = false;
            if (this.bout != null) {
                this.bout.close();
            }
            if (this.client != null) {
                this.client.close();
            }
        } catch (Exception e) {
            
        }
    }
}
