package ir.karcook.ViewPresenter.Login;

import android.content.Context;

import java.util.Map;

import ir.karcook.Base.BasePresnter;
import ir.karcook.Base.BaseView;


/**
 * Created by John Cale on 6/5/2018.
 */

public interface LoginContract {

    interface presenter extends BasePresnter {
        Context getContext();

        void login(String userName, String password);

        void requestForgetCode(Map<String , String> params);


    }

    interface veiw extends BaseView<presenter> {

        Context getContext();

        void loginSuccess();

        void userDisabled(String error);

        void loginFailed(String error);

        void requestVerifyCodeSuccess(String data);

        void requestVerifyCodeFailed(String error);


    }
}
