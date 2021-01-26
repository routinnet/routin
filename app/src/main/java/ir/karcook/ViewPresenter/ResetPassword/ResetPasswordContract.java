package ir.karcook.ViewPresenter.ResetPassword;

import android.content.Context;

import java.util.Map;

import ir.karcook.Base.BasePresnter;
import ir.karcook.Base.BaseView;

public interface ResetPasswordContract {

    interface presenter extends BasePresnter {
        Context getContext();

        void resetPass(String phone, String code,String confirmCode, String password);

        void requestForgetCode(Map<String, String> params);


    }

    interface view extends BaseView<presenter> {
        Context getContext();

        void resetPassSuccess();

        void resetPassFailed(String string);

        void requestVerifyCodeSuccess();

        void requestVerifyCodeFailed(String error);

    }

}