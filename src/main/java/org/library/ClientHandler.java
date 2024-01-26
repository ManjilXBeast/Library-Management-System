package org.library;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;

public class ClientHandler extends Thread {
    private ServerData db;
    private Socket clientSocket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public ClientHandler(Socket clientSocket, ServerData db) {
        this.clientSocket = clientSocket;
        this.db = db;
        try {
            this.outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            this.inputStream = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            System.out.println("Error while making input stream.");
            System.out.println("Error while making output stream.");
        }
    }

    @Override
    public void run() {
        System.out.println("Handling.");

        // Listen for client messages
        while (true) {
            try {
                Object req = this.inputStream.readObject();
                System.out.println(req);

                if (req instanceof BookAddMsg msg) {
                    System.out.println("Adding");
                    this.db.addEvent(msg.book);
                }
                else if (req instanceof BookListRequest) {
                    this.outputStream.writeObject(new BookListResponse(this.db.getBooks()));
                }
            }
            catch (EOFException e) {
                break;
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error while reading messages");
            } catch (SQLException e) {
                System.out.println("Database error");
            }
        }
    }
}
