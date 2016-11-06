package org.xdty.kindle.data;

import org.xdty.kindle.application.Application;
import org.xdty.kindle.module.Book;
import org.xdty.kindle.module.Books;
import org.xdty.kindle.module.Node;
import org.xdty.kindle.module.Review;
import org.xdty.kindle.module.database.Database;
import org.xdty.kindle.utils.Constants;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class BookRepository implements BookDataSource {

    @Inject
    BookService mBookService;

    @Inject
    Database mDatabase;

    private Books mBooks;

    private Map<String, Review> mReviewMap = new HashMap<>();

    public BookRepository() {
        Application.getAppComponent().inject(this);
    }

    @Override
    public Observable<List<Book>> getDailyBooks() {
        return Observable.create(new Observable.OnSubscribe<List<Book>>() {
            @Override
            public void call(Subscriber<? super List<Book>> subscriber) {
                try {
                    if (mBooks == null || (System.currentTimeMillis() - mBooks.getTimestamp()
                            > Constants.CACHE_TIME)) {
                        mBooks = mBookService.getBooks().execute().body();
                        mBooks.setTimestamp(System.currentTimeMillis());

                        mReviewMap.clear();

                        // cache reviews
                        for (Book book : mBooks.getBooks()) {
                            Review review = new Review();
                            review.setItemId(book.getItemId());
                            review.setEditorialReview(book.getEditorialReview());
                            mReviewMap.put(book.getItemId(), review);
                        }
                    }
                    subscriber.onNext(mBooks.getBooks());
                    subscriber.onCompleted();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<Book>> getFreeCnBooks() {
        return Observable.create(new Observable.OnSubscribe<List<Book>>() {
            @Override
            public void call(Subscriber<? super List<Book>> subscriber) {
                subscriber.onNext(mDatabase.getCnBooksSync());
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<Book>> getFreeEnBooks() {
        return Observable.create(new Observable.OnSubscribe<List<Book>>() {
            @Override
            public void call(Subscriber<? super List<Book>> subscriber) {
                subscriber.onNext(mDatabase.getEnBooksSync());
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<Book>> getBooks(Mode mode) {
        switch (mode) {
            case FREE_CN:
                return getFreeCnBooks();
            case FREE_EN:
                return getFreeEnBooks();
            case DAILY_DEALS:
            default:
                return getDailyBooks();
        }
    }

    @Override
    public Observable<List<Node>> getBookNodes(final String itemId) {
        return Observable.create(new Observable.OnSubscribe<List<Node>>() {
            @Override
            public void call(Subscriber<? super List<Node>> subscriber) {
                subscriber.onNext(mDatabase.getBookNodesSync(itemId));
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<Review>> getReviews(final String itemId) {
        return Observable.create(new Observable.OnSubscribe<List<Review>>() {
            @Override
            public void call(Subscriber<? super List<Review>> subscriber) {
                subscriber.onNext(mDatabase.getReviewsSync(itemId));
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Review> getReview(final String itemId) {
        return Observable.create(new Observable.OnSubscribe<Review>() {
            @Override
            public void call(Subscriber<? super Review> subscriber) {
                if (mReviewMap.containsKey(itemId)) {
                    subscriber.onNext(mReviewMap.get(itemId));
                } else {
                    subscriber.onNext(mDatabase.getReviewsSync(itemId).get(0));
                }

                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Book> getBook(final String itemId) {
        return Observable.create(new Observable.OnSubscribe<Book>() {
            @Override
            public void call(Subscriber<? super Book> subscriber) {
                subscriber.onNext(mDatabase.getBookSync(itemId));
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
