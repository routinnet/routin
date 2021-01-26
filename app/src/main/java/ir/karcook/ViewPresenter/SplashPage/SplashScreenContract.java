package ir.karcook.ViewPresenter.SplashPage;

import android.content.Context;

import ir.karcook.Base.BasePresnter;
import ir.karcook.Base.BaseView;

public interface SplashScreenContract {

    interface presenter extends BasePresnter {
        Context getContext();

        void getProfile();

    }

    interface view extends BaseView<presenter> {

        Context getContext();

        void getProfileSuccess();
        void getProfileFailed(String error);

        void tokenExpired();
    }

}