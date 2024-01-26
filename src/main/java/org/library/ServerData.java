package org.library;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServerData {
    private Connection connection;

    final String dbName = "library";
    final String dbUrl = "jdbc:mysql://localhost:3306/" + dbName;
    final String username = "root";
    final String password = "";

    public ServerData() {
        try {
            this.connection = DriverManager.getConnection(this.dbUrl, this.username, this.password);
        } catch (SQLException e) {
            System.out.println("Error while making database");
        }
    }

    void addEvent(Book book) throws SQLException {
        PreparedStatement stmt = this.connection.prepareStatement("INSERT INTO" +
                " books(title, author, available) " +
                " VALUES(?, ?, ?)"
        );
        stmt.setString(1, book.title);
        stmt.setString(2, book.author);
        stmt.setBoolean(3, book.available);
        stmt.executeUpdate();
        stmt.close();
    }

    public List<Book> getBooks() throws SQLException {
        Statement stmt = this.connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM books");
        List<Book> books = new ArrayList<>();
        while (rs.next()) {
            Book book = new Book(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getBoolean("available")
            );
            books.add(book);
        }
        return books;
    }

    // Methods for saving and retrieving data from the database
}
