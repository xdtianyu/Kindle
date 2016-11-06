package org.xdty.kindle.data;

import org.xdty.kindle.module.Book;
import org.xdty.kindle.module.Node;
import org.xdty.kindle.module.Review;

import java.util.List;

import rx.Observable;

public interface BookDataSource {

    Observable<List<Book>> getDailyBooks();

    Observable<List<Book>> getFreeCnBooks();

    Observable<List<Book>> getFreeEnBooks();

    Observable<List<Book>> getBooks(Mode mode);

    Observable<List<Node>> getBookNodes(String itemId);

    Observable<List<Review>> getReviews(String itemId);

    Observable<Review> getReview(String itemId);

    Observable<Book> getBook(String itemId);
}
