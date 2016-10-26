package org.xdty.kindle.module.database;

import org.xdty.kindle.module.Book;

import java.util.List;

public interface Database {

    List<Book> getCnBooksSync();

    List<Book> getEnBooksSync();
}
