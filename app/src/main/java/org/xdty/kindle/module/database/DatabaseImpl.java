package org.xdty.kindle.module.database;

import org.xdty.kindle.application.Application;
import org.xdty.kindle.module.Book;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import io.requery.Persistable;
import io.requery.sql.EntityDataStore;

public class DatabaseImpl implements Database {

    private final static int PAGE_SIZE = 50;
    @Inject
    EntityDataStore<Persistable> mDataStore;
    private File mDbFile;

    private int mCurrentPage = 0;

    private DatabaseImpl() {
        Application.getAppComponent().inject(this);
    }

    public static DatabaseImpl getInstance() {
        return SingletonHelper.INSTANCE;
    }

    @Override
    public List<Book> getCnBooksSync() {
        return mDataStore.select(Book.class).limit(PAGE_SIZE).offset(mCurrentPage).get().toList();
    }

    @Override
    public List<Book> getEnBooksSync() {
        return null;
    }

    private static class SingletonHelper {
        private final static DatabaseImpl INSTANCE = new DatabaseImpl();
    }

}
