package org.library;

public class BookAddMsg implements java.io.Serializable {
    public Book book;
    BookAddMsg(Book e) {
        this.book = e;
    }
}
