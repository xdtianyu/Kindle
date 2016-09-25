package org.xdty.kindle.di.modules;

import org.xdty.kindle.application.Application;
import org.xdty.kindle.data.BookDataSource;
import org.xdty.kindle.data.BookRepository;
import org.xdty.kindle.data.BookService;
import org.xdty.kindle.utils.Constants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

    private Application app;

    public AppModule(Application application) {
        app = application;
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
        return new OkHttpClient();
    }

    @Singleton
    @Provides
    public BookService provideBookService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(BookService.class);
    }

}
