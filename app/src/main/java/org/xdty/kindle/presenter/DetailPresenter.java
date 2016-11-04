package org.xdty.kindle.presenter;

import org.xdty.kindle.contract.DetailContract;

public class DetailPresenter implements DetailContract.Presenter {

    private DetailContract.View mView;

    public DetailPresenter(DetailContract.View view) {
        mView = view;
    }

    @Override
    public void start() {

    }
}
