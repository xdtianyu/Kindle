package org.xdty.kindle.di.modules;

import android.content.Context;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;

import org.xdty.kindle.BuildConfig;
import org.xdty.kindle.R;
import org.xdty.kindle.application.Application;
import org.xdty.kindle.data.BookDataSource;
import org.xdty.kindle.data.BookRepository;
import org.xdty.kindle.data.BookService;
import org.xdty.kindle.module.Models;
import org.xdty.kindle.module.database.Database;
import org.xdty.kindle.module.database.DatabaseImpl;
import org.xdty.kindle.utils.Constants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.requery.Persistable;
import io.requery.android.sqlite.DatabaseSource;
import io.requery.sql.Configuration;
import io.requery.sql.EntityDataStore;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static org.xdty.kindle.utils.Constants.DB_NAME;
import static org.xdty.kindle.utils.Constants.DB_VERSION;

@Module
public class AppModule {
    private static final String TAG = AppModule.class.getSimpleName();

    private Application app;
    private OkHttpClient mOkHttpClient;

    public AppModule(Application application) {
        app = application;

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                HttpUrl url = request.url()
                        .newBuilder()
                        .addQueryParameter("timestamp",
                                Long.toString(System.currentTimeMillis() / 1000 / 60))
                        .build();
                request = request.newBuilder().url(url).build();
                return chain.proceed(request);
            }
        };

        mOkHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(interceptor)
                .build();
    }

    @Singleton
    @Provides
    public Application provideApplication() {
        return app;
    }

    @Singleton
    @Provides
    public BookDataSource provideBookDataSource() {
        return new BookRepository();
    }

    @Singleton
    @Provides
    public OkHttpClient provideOkHttpClient() {
        return mOkHttpClient;
    }

    @Singleton
    @Provides
    public BookService provideBookService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(BookService.class);
    }

    @Singleton
    @Provides
    public Database provideDatabase() {
        return DatabaseImpl.getInstance();
    }

    @Singleton
    @Provides
    public EntityDataStore<Persistable> provideDatabaseSource() {
        Observable.<Void>just(null).observeOn(Schedulers.io()).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                raw2data(app, DB_NAME, R.raw.books);
            }
        });

        DatabaseSource source = new DatabaseSource(app, Models.DEFAULT, DB_NAME, DB_VERSION);
        source.setLoggingEnabled(BuildConfig.DEBUG);
        Configuration configuration = source.getConfiguration();

        return new EntityDataStore<>(configuration);
    }

    @Singleton
    @Provides
    public RequestManager provideGlide() {
        OkHttpUrlLoader.Factory factory = new OkHttpUrlLoader.Factory(mOkHttpClient);
        Glide.get(app).register(GlideUrl.class, InputStream.class, factory);
        return Glide.with(app);
    }

    private void raw2data(Context context, String filename, int raw) {

        try {
            InputStream inputStream = context.getResources().openRawResource(raw);

            File cacheFile = context.getDatabasePath(filename);

            if (cacheFile.exists()) {
                if (cacheFile.length() == inputStream.available()) {
                    return;
                } else if (!cacheFile.delete()) {
                    Log.d(TAG, "cache file delete failed.");
                } else {
                    Log.d(TAG, "update cached database.");
                }
            }

            FileOutputStream fileOutputStream = new FileOutputStream(cacheFile);

            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, length);
            }
            fileOutputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
