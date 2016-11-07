package org.xdty.kindle.contract;

import org.xdty.kindle.module.Book;

public interface DetailContract {

    interface View extends BaseView<Presenter> {
        void updateReview(String review);

        void openTab(String url);
    }

    interface Presenter extends BasePresenter {
        void start(Book itemId);

        void clickFab();
    }
}
