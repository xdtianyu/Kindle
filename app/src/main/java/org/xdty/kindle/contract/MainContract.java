package org.xdty.kindle.contract;

import org.xdty.kindle.data.Mode;
import org.xdty.kindle.module.Book;

import java.util.List;

public interface MainContract {

    interface View extends BaseView<Presenter> {
        void refresh(List<Book> books);
    }

    interface Presenter extends BasePresenter {
        void loadBooks();

        void getBookNodes(Book book);

        void getReviews(String itemId);

        void setMode(Mode mode);
    }
}
