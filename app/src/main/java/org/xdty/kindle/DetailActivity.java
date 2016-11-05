package org.xdty.kindle;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.xdty.kindle.contract.DetailContract;
import org.xdty.kindle.di.DaggerDetailComponent;
import org.xdty.kindle.di.modules.DetailModule;
import org.xdty.kindle.module.Book;

import javax.inject.Inject;

public class DetailActivity extends AppCompatActivity implements DetailContract.View {

    public final static String ARG_BOOK = "book";
    public final static String ARG_REVIEW = "review";
    private static final String TAG = DetailActivity.class.getSimpleName();
    @Inject
    DetailContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String review = getIntent().getStringExtra(ARG_REVIEW);
        Book book = getIntent().getParcelableExtra(ARG_BOOK);

        DaggerDetailComponent.builder().detailModule(new DetailModule(this))
                .build()
                .inject(this);

        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        TextView reviewText = (TextView) findViewById(R.id.review);
        reviewText.setText(review);
        setTitle(book.getTitle());
    }
}
