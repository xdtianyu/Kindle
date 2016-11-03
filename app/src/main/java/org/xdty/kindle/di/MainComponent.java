package org.xdty.kindle.di;

import org.xdty.kindle.MainActivity;
import org.xdty.kindle.di.modules.AppModule;
import org.xdty.kindle.di.modules.MainModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = { MainModule.class, AppModule.class })
public interface MainComponent {
    void inject(MainActivity mainActivity);
}
