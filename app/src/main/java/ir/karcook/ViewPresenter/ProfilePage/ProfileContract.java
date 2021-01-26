package ir.karcook.ViewPresenter.ProfilePage;

import android.content.Context;

import java.util.Map;

import ir.karcook.Base.BasePresnter;
import ir.karcook.Base.BaseView;
import ir.karcook.Models.ProfileAPIModel;

public interface ProfileContract {

    interface presenter extends BasePresnter {
        Context getContext();

        void changePass(String oldPass, String newPass);

        void getProfile();

        void getVerifyCode(String phone);

        void doChangeAge(Map<String, Integer> params);
    }

    interface view extends BaseView<presenter> {

        Context getContext();

        void changePassSuccess();

        void changePassFailed(String error);

        void getProfileSuccess(ProfileAPIModel model);

        void getProfileFailed(String error);

        void getVerifyCodeSuccess();

        void getVerifyCodeFailed(String error);

        void doChangeAgeSuccess();

        void doChangeAgeFailed(String error);


    }

}