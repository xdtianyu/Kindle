package org.xdty.kindle.presenter;

import org.xdty.kindle.application.Application;
import org.xdty.kindle.contract.DetailContract;
import org.xdty.kindle.data.BookDataSource;
import org.xdty.kindle.module.Review;

import javax.inject.Inject;

import rx.functions.Action1;

public class DetailPresenter implements DetailContract.Presenter {

    private DetailContract.View mView;

    @Inject
    BookDataSource mBookDataSource;

    public DetailPresenter(DetailContract.View view) {
        mView = view;
        Application.getAppComponent().inject(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void updateReview(String itemId) {
        mBookDataSource.getReview(itemId).subscribe(new Action1<Review>() {
            @Override
            public void call(Review review) {
                mView.refreshReview(review);
            }
        });
    }
}
