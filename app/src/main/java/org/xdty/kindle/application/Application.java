package org.xdty.kindle.application;

import org.xdty.kindle.di.AppComponent;
import org.xdty.kindle.di.DaggerAppComponent;
import org.xdty.kindle.di.modules.AppModule;

public class Application extends android.app.Application {

    private static AppComponent sAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        sAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }

}
