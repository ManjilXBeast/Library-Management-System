package org.library;

import javax.swing.*;
import java.awt.*;
import java.io.EOFException;
import java.io.IOException;
import java.util.List;

public class ClientGui {
    ClientSock sock;
    JPanel bookList = new JPanel(new GridLayout(0, 1, 10, 10));

    public ClientGui(ClientSock sock) {
        this.sock = sock;
    }

    void show() {
        Dimension frameSize = new Dimension(1000, 700);
        JFrame frame = new JFrame("Event booking system");
        frame.setSize(frameSize);
        frame.setMinimumSize(frameSize);

        JPanel container = new JPanel(new BorderLayout());

        int fieldSize = 15;
        JPanel controls = new JPanel();
        JTextField title = new JTextField(fieldSize);
        JTextField author = new JTextField(fieldSize);
        JTextField available = new JTextField("(yes/no)", fieldSize);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            try {
                this.sock.outputStream.writeObject(new BookAddMsg(
                    new Book(
                        title.getText(),
                        author.getText(),
                        available.getText().equals("yes") ? true : false
                    )
                ));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        JButton listBooksButton = new JButton("List Books");
        listBooksButton.addActionListener(e -> {
            try {
                this.sock.outputStream.writeObject(new BookListRequest());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        controls.add(new JLabel("Title"));
        controls.add(title);
        controls.add(new JLabel("Author"));
        controls.add(author);
        controls.add(new JLabel("Available"));
        controls.add(available);

        controls.add(addButton);
        controls.add(listBooksButton);

        container.add(controls, BorderLayout.NORTH);
        JScrollPane scrollPane = new JScrollPane(this.bookList);
        container.add(scrollPane, BorderLayout.CENTER);

        frame.add(container);
        frame.setVisible(true);
    }

    void setBooks(List<Book> books) {
        this.bookList.removeAll();
        for (Book b : books) {
            System.out.println("Adding book " + b);
            JPanel panel = new JPanel();
            panel.add(new JLabel("Title: " + b.title));
            panel.add(new JLabel("Author: " + b.author));
            panel.add(new JLabel("Available: " + (b.available ? "yes" : "no")));
            this.bookList.add(panel);
        }
        this.bookList.revalidate();
        this.bookList.repaint();
    }

    void listen() {
        System.out.println("Listening for server messages");
        while (true) {
            try {
                Object res = this.sock.inputStream.readObject();
                System.out.println(res);

                if (res instanceof BookListResponse msg) {
                    setBooks(msg.books);
                }
            }
            catch (EOFException e) {
                break;
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error while reading server messages");
            }
        }
    }
}