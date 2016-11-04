package org.xdty.kindle.di.modules;

import org.xdty.kindle.contract.DetailContract;
import org.xdty.kindle.presenter.DetailPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class DetailModule {

    private DetailContract.View mView;

    public DetailModule(DetailContract.View view) {
        mView = view;
    }

    @Provides
    DetailContract.View provideView() {
        return mView;
    }

    @Provides
    DetailContract.Presenter providePresenter() {
        return new DetailPresenter(mView);
    }

}
