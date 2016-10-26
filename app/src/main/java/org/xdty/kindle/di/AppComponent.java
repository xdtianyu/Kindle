package org.xdty.kindle.di;

import org.xdty.kindle.MainActivity;
import org.xdty.kindle.data.BookRepository;
import org.xdty.kindle.di.modules.AppModule;
import org.xdty.kindle.module.database.DatabaseImpl;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    void inject(BookRepository bookRepository);

    void inject(MainActivity mainActivity);

    void inject(DatabaseImpl database);
}
