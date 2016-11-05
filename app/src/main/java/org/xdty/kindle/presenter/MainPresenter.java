package org.xdty.kindle.presenter;

import android.util.Log;

import org.xdty.kindle.application.Application;
import org.xdty.kindle.contract.MainContract;
import org.xdty.kindle.data.BookDataSource;
import org.xdty.kindle.data.Mode;
import org.xdty.kindle.module.Book;
import org.xdty.kindle.module.Node;
import org.xdty.kindle.module.Review;

import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;

public class MainPresenter implements MainContract.Presenter {

    private static final String TAG = MainPresenter.class.getSimpleName();

    @Inject
    BookDataSource mBookDataSource;

    private MainContract.View mView;

    private Mode mMode = Mode.DAILY_DEALS;

    public MainPresenter(MainContract.View view) {
        mView = view;
        Application.getAppComponent().inject(this);
    }

    @Override
    public void start() {
        loadBooks();
    }

    @Override
    public void loadBooks() {
        mBookDataSource.getBooks(mMode).subscribe(new Action1<List<Book>>() {
            @Override
            public void call(List<Book> books) {
                mView.refresh(books);
            }
        });
    }

    @Override
    public void getBookNodes(Book book) {
        mBookDataSource.getBookNodes(book.getItemId()).subscribe(new Action1<List<Node>>() {
            @Override
            public void call(List<Node> nodes) {
                Log.e(TAG, "nodes: " + nodes);
            }
        });
    }

    @Override
    public void getReviews(String itemId) {
        mBookDataSource.getReviews(itemId).subscribe(new Action1<List<Review>>() {
            @Override
            public void call(List<Review> reviews) {
                Log.e(TAG, "reviews: " + reviews);
            }
        });
    }

    @Override
    public void setMode(Mode mode) {
        mMode = mode;
    }
}
