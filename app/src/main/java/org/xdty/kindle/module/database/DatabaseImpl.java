package org.xdty.kindle.module.database;

import org.xdty.kindle.application.Application;
import org.xdty.kindle.module.Book;

import java.util.List;

import javax.inject.Inject;

import io.requery.Persistable;
import io.requery.sql.EntityDataStore;

public class DatabaseImpl implements Database {

    private final static int PAGE_SIZE = 50;
    @Inject
    EntityDataStore<Persistable> mDataStore;

    private int mCurrentPage = 0;

    private DatabaseImpl() {
        Application.getAppComponent().inject(this);
    }

    public static DatabaseImpl getInstance() {
        return SingletonHelper.INSTANCE;
    }

    @Override
    public List<Book> getCnBooksSync() {
        return mDataStore.select(Book.class)
                .where(Book.LANGUAGES.eq("chinese"))
                .or(Book.LANGUAGES.eq("traditional_chinese"))
                .limit(PAGE_SIZE)
                .offset(mCurrentPage)
                .get()
                .toList();
    }

    @Override
    public List<Book> getEnBooksSync() {
        return mDataStore.select(Book.class)
                .where(Book.LANGUAGES.ne("chinese"))
                .and(Book.LANGUAGES.ne("traditional_chinese"))
                .limit(PAGE_SIZE)
                .offset(mCurrentPage)
                .get()
                .toList();
    }

    private static class SingletonHelper {
        private final static DatabaseImpl INSTANCE = new DatabaseImpl();
    }

}
