package org.library;

public class ClientMain {
    public static void main(String[] args) {
        ClientSock sock = new ClientSock();
        ClientGui gui = new ClientGui(sock);
        gui.show();
        gui.listen();
    }
}
