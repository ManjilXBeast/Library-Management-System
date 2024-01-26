package org.library;

import java.util.List;

public class BookListResponse implements java.io.Serializable {
    final List<Book> books;

    public BookListResponse(List<Book> books) {
        this.books = books;
    }
}
