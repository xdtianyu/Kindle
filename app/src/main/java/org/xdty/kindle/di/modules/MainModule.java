package org.xdty.kindle.di.modules;

import org.xdty.kindle.contract.MainContract;
import org.xdty.kindle.presenter.MainPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {

    private MainContract.View mView;

    public MainModule(MainContract.View view) {
        mView = view;
    }

    @Provides
    MainContract.View provideView() {
        return mView;
    }

    @Provides
    MainContract.Presenter providePresenter() {
        return new MainPresenter(mView);
    }

}
