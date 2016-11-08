package org.xdty.kindle;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import org.xdty.kindle.contract.DetailContract;
import org.xdty.kindle.di.DaggerDetailComponent;
import org.xdty.kindle.di.modules.DetailModule;
import org.xdty.kindle.module.Book;

import javax.inject.Inject;

import static org.xdty.kindle.R.id.review;

public class DetailActivity extends AppCompatActivity implements DetailContract.View {

    public final static String ARG_BOOK = "book";
    private static final String TAG = DetailActivity.class.getSimpleName();
    @Inject
    DetailContract.Presenter mPresenter;

    private TextView mReviewText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                mPresenter.clickFab();
            }
        });

        mReviewText = (TextView) findViewById(review);

        Book book = getIntent().getParcelableExtra(ARG_BOOK);
        setTitle(book.getTitle());

        mPresenter.start(book);
    }

    @Override
    public void updateReview(String review) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mReviewText.setText(Html.fromHtml(review, Html.FROM_HTML_MODE_LEGACY));
        } else {
            //noinspection deprecation
            mReviewText.setText(Html.fromHtml(review));
        }
    }

    public void openTab(String url) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.addDefaultShareMenuItem();
        builder.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(url));
    }
}
