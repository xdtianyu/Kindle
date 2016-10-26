package org.xdty.kindle.di.modules;

import org.xdty.kindle.R;
import org.xdty.kindle.application.Application;
import org.xdty.kindle.data.BookDataSource;
import org.xdty.kindle.data.BookRepository;
import org.xdty.kindle.data.BookService;
import org.xdty.kindle.module.database.Database;
import org.xdty.kindle.module.database.DatabaseImpl;
import org.xdty.kindle.utils.Constants;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.xdty.kindle.utils.Constants.DB_NAME;

@Module
public class AppModule {

    private Application app;
    private OkHttpClient mOkHttpClient;

    public AppModule(Application application) {
        app = application;

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

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
        DatabaseImpl.getInstance().init(app, DB_NAME, R.raw.books);
        return DatabaseImpl.getInstance();
    }

}
