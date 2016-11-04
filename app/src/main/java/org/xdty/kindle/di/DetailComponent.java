package org.xdty.kindle.di;

import org.xdty.kindle.DetailActivity;
import org.xdty.kindle.di.modules.AppModule;
import org.xdty.kindle.di.modules.DetailModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = { DetailModule.class, AppModule.class })
public interface DetailComponent {

    void inject(DetailActivity detailActivity);
}
