package org.xdty.kindle.di;

import org.xdty.kindle.data.BookRepository;
import org.xdty.kindle.di.modules.AppModule;
import org.xdty.kindle.module.database.DatabaseImpl;
import org.xdty.kindle.presenter.MainPresenter;
import org.xdty.kindle.view.BooksAdapter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    void inject(BookRepository bookRepository);

    void inject(DatabaseImpl database);

    void inject(BooksAdapter booksAdapter);

    void inject(MainPresenter mainPresenter);
}
