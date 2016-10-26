package org.xdty.kindle.module.database;

import android.content.Context;

import org.xdty.kindle.module.Book;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseImpl implements Database {

    private final static String PAGE_SIZE = "50";

    private File mDbFile;

    private int mCurrentPage = 0;

    private DatabaseImpl() {

    }

    public static DatabaseImpl getInstance() {
        return SingletonHelper.INSTANCE;
    }

    public void init(Context context, String filename, int raw) {
        try {
            mDbFile = createCacheFile(context, filename, raw);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Book> getCnBooksSync() {

        List<Book> books = new ArrayList<>();

        return books;
    }

    @Override
    public List<Book> getEnBooksSync() {
        return null;
    }

    private synchronized File createCacheFile(Context context, String filename, int raw)
            throws IOException {
        File cacheFile = new File(context.getCacheDir(), filename);

        if (cacheFile.exists()) {
            return cacheFile;
        }

        InputStream inputStream = context.getResources().openRawResource(raw);
        FileOutputStream fileOutputStream = new FileOutputStream(cacheFile);

        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            fileOutputStream.write(buffer, 0, length);
        }

        try {
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cacheFile;
    }

    private static class SingletonHelper {
        private final static DatabaseImpl INSTANCE = new DatabaseImpl();
    }

}
