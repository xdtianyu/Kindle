package org.xdty.kindle.data;

import org.xdty.kindle.application.Application;
import org.xdty.kindle.module.Book;
import org.xdty.kindle.module.Books;

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

    public BookRepository() {
        Application.getAppComponent().inject(this);
    }

    @Override
    public Observable<List<Book>> getBooks() {
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
}
