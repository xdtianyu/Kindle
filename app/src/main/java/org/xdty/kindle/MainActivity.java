package org.xdty.kindle;

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
import android.view.Menu;
import android.view.MenuItem;

import org.xdty.kindle.application.Application;
import org.xdty.kindle.data.BookDataSource;
import org.xdty.kindle.data.Mode;
import org.xdty.kindle.module.Book;
import org.xdty.kindle.view.BooksAdapter;

import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;

// TODO: mvp

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Inject
    BookDataSource mBookDataSource;

    private RecyclerView mRecyclerView;
    private Toolbar mToolbar;

    private DrawerLayout mDrawer;

    private BooksAdapter mBooksAdapter;

    private Mode mMode = Mode.DAILY_DEALS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Application.getAppComponent().inject(this);

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

        mRecyclerView.setAdapter(mBooksAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadBooks();
    }

    private void loadBooks() {
        mBookDataSource.getBooks(mMode).subscribe(new Action1<List<Book>>() {
            @Override
            public void call(List<Book> books) {
                mBooksAdapter.refresh(books);
            }
        });
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
                mMode = Mode.FREE_CN;
                break;
            case R.id.nav_free_english:
                mMode = Mode.FREE_EN;
                break;
            case R.id.nav_daily_deals:
                mMode = Mode.DAILY_DEALS;
                break;
        }
        loadBooks();
        return true;
    }
}
