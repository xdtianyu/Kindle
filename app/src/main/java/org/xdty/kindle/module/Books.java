package org.xdty.kindle.module;

import java.util.List;

public class Books {

    private List<Book> books;
    private String time;

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
