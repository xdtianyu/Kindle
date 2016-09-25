package org.xdty.kindle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.xdty.kindle.application.Application;
import org.xdty.kindle.data.BookDataSource;
import org.xdty.kindle.module.Book;
import org.xdty.kindle.view.BooksAdapter;

import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {

    @Inject
    BookDataSource mBookDataSource;

    private RecyclerView mRecyclerView;
    private BooksAdapter mBooksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Application.getAppComponent().inject(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.list);

        mBooksAdapter = new BooksAdapter();

        mRecyclerView.setAdapter(mBooksAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mBookDataSource.getBooks().subscribe(new Action1<List<Book>>() {
            @Override
            public void call(List<Book> books) {
                mBooksAdapter.refresh(books);
            }
        });
    }
}
