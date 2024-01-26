package org.library;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSock {
    private ServerSocket serverSocket;
    private ServerData db;

    ServerSock(ServerData db){
        try {
            this.serverSocket = new ServerSocket(4000);
            this.db = db;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error while making server.");
        }
    }

    public void start() {
        System.out.println("Starting server..");
        try {
            while (true) {
                Socket sock = this.serverSocket.accept();
                ClientHandler handler = new ClientHandler(sock, this.db);
                handler.start();
            }
        } catch (IOException e) {
            System.out.println("Error while accepting client");
        }
    }
}

