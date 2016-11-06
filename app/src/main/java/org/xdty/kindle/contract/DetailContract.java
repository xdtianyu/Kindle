package org.xdty.kindle.contract;

import org.xdty.kindle.module.Review;

public interface DetailContract {

    interface View extends BaseView<Presenter> {
        void refreshReview(Review review);
    }

    interface Presenter extends BasePresenter {

        void updateReview(String itemId);
    }
}
