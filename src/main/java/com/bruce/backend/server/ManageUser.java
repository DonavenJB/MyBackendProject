package com.bruce.backend.server;

import com.bruce.backend.common.Reply;
import com.bruce.backend.common.ObjectSocketConnection;

public class ManageUser {
    protected ObjectSocketConnection client;

    // Constructor to initialize the client socket
    public ManageUser(ObjectSocketConnection client) {
        this.client = client;
    }

    // Method used by WebContentFetcher to send a reply to the client
    public void write(Reply reply) {
        this.client.writeObject(reply);
    }
}
