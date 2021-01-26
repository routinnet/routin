package ir.karcook.ViewPresenter.SplashPage;

import android.content.Context;
import android.content.SharedPreferences;

import ir.karcook.Base.UseCase;
import ir.karcook.Models.ProfileAPIModel;
import ir.karcook.R;
import ir.karcook.UseCase.GetProfile_useCase;

public class SplashScreenPresenter implements SplashScreenContract.presenter {
    private SplashScreenContract.view iv;
    private Context context;
    GetProfile_useCase getProfile_useCase;


    public SplashScreenPresenter(SplashScreenContract.view iv, GetProfile_useCase getProfile_useCase) {
        this.iv = iv;
        setContext(iv.getContext());
        this.getProfile_useCase = getProfile_useCase;
    }


    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public Context getContext() {
        return this.context;
    }

    @Override
    public void getProfile() {
        getProfile_useCase.execute(null, new UseCase.CallBack<ProfileAPIModel>() {
            @Override
            public void onSuccess(ProfileAPIModel response) {
                iv.getProfileSuccess();
            }

            @Override
            public void onError(String error) {
                if (error.equals(getContext().getResources().getString(R.string.tokenExpired)))
                    iv.tokenExpired();
                else
                    iv.getProfileFailed(error);
            }
        }, context);
    }

}