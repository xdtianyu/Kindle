package org.xdty.kindle.data;

import org.xdty.kindle.module.Book;

import java.util.List;

import rx.Observable;

public interface BookDataSource {

    Observable<List<Book>> getDailyBooks();

    Observable<List<Book>> getFreeCnBooks();

    Observable<List<Book>> getFreeEnBooks();

    Observable<List<Book>> getBooks(Mode mode);

}
