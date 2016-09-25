package org.xdty.kindle.data;

import org.xdty.kindle.module.Book;

import java.util.List;

import rx.Observable;

public interface BookDataSource {

    Observable<List<Book>> getBooks();

}
