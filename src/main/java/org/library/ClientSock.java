package org.library;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientSock {
    Socket sock;
    ObjectInputStream inputStream;
    ObjectOutputStream outputStream;
    ClientSock() {
        try {
            this.sock = new Socket("localhost", 4000);
            this.outputStream = new ObjectOutputStream(this.sock.getOutputStream());
            this.inputStream = new ObjectInputStream(this.sock.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
