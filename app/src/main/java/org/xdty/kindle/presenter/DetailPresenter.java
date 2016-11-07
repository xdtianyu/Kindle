package org.xdty.kindle.presenter;

import org.xdty.kindle.application.Application;
import org.xdty.kindle.contract.DetailContract;
import org.xdty.kindle.data.BookDataSource;
import org.xdty.kindle.module.Book;
import org.xdty.kindle.module.Review;

import javax.inject.Inject;

import rx.functions.Action1;

public class DetailPresenter implements DetailContract.Presenter {

    @Inject
    BookDataSource mBookDataSource;
    private DetailContract.View mView;
    private Book mBook;
    private Review mReview;

    public DetailPresenter(DetailContract.View view) {
        mView = view;
        Application.getAppComponent().inject(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void clickFab() {
        if (mBook != null) {
            mView.openTab(mBook.getUrl());
        }
    }

    @Override
    public void start(Book book) {

        mBook = book;

        if (book.getEditorialReview() == null) {
            mBookDataSource.getReview(book.getItemId()).subscribe(new Action1<Review>() {
                @Override
                public void call(Review review) {
                    mReview = review;
                    mView.updateReview(review.getEditorialReview());
                }
            });
        }
    }
}
