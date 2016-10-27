package org.xdty.kindle.data;

import org.xdty.kindle.application.Application;
import org.xdty.kindle.module.Book;
import org.xdty.kindle.module.Books;
import org.xdty.kindle.module.Node;
import org.xdty.kindle.module.database.Database;

import java.io.IOException;
import java.util.List;

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

    public BookRepository() {
        Application.getAppComponent().inject(this);
    }

    @Override
    public Observable<List<Book>> getDailyBooks() {
        return Observable.create(new Observable.OnSubscribe<List<Book>>() {
            @Override
            public void call(Subscriber<? super List<Book>> subscriber) {
                try {
                    Books books = mBookService.getBooks().execute().body();
                    subscriber.onNext(books.getBooks());
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
}
