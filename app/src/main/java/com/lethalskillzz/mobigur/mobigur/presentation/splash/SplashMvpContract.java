package com.lethalskillzz.mobigur.mobigur.presentation.splash;

import com.lethalskillzz.mobigur.mvp.Mvp;

/**
 * Created by ibrahimabdulkadir on 12/04/2017.
 */

public class SplashMvpContract {

    public interface View extends Mvp.View {

        void endSplash();
    }

    public interface Presenter extends Mvp.Presenter<SplashMvpContract.View> {
        void startSplash();
    }
}
