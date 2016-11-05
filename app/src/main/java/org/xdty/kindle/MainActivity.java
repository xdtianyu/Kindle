package org.xdty.kindle;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.xdty.kindle.contract.MainContract;
import org.xdty.kindle.data.Mode;
import org.xdty.kindle.di.DaggerMainComponent;
import org.xdty.kindle.di.modules.MainModule;
import org.xdty.kindle.module.Book;
import org.xdty.kindle.view.BooksAdapter;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity
        implements MainContract.View, NavigationView.OnNavigationItemSelectedListener,
        BooksAdapter.ItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Inject
    MainContract.Presenter mPresenter;

    private RecyclerView mRecyclerView;
    private Toolbar mToolbar;
    private DrawerLayout mDrawer;
    private BooksAdapter mBooksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DaggerMainComponent.builder()
                .mainModule(new MainModule(this))
                .build()
                .inject(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        setSupportActionBar(mToolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mBooksAdapter = new BooksAdapter();
        mBooksAdapter.setItemClickListener(this);

        mRecyclerView.setAdapter(mBooksAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mPresenter.start();
    }

    @Override
    public void onBackPressed() {

        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        mDrawer.closeDrawer(GravityCompat.START);

        switch (id) {
            case R.id.nav_setting:
                // start setting activity
                return true;
            case R.id.nav_free_chinese:
                mPresenter.setMode(Mode.FREE_CN);
                break;
            case R.id.nav_free_english:
                mPresenter.setMode(Mode.FREE_EN);
                break;
            case R.id.nav_daily_deals:
                mPresenter.setMode(Mode.DAILY_DEALS);
                break;
        }
        mPresenter.loadBooks();
        return true;
    }

    @Override
    public void onItemClick(Book book) {
        if (book.getNodes() == null) {
            mPresenter.getBookNodes(book);
        } else {
            Log.e(TAG, "nodes: " + book.getNodes());
        }
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.ARG_BOOK, book);
        intent.putExtra(DetailActivity.ARG_REVIEW, book.getEditorialReview());
        startActivity(intent);
    }

    @Override
    public void refresh(List<Book> books) {
        mBooksAdapter.refresh(books);
    }
}
