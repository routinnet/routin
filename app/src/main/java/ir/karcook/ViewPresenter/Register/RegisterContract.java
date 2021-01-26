package ir.karcook.ViewPresenter.Register;

import android.content.Context;

import java.util.Map;

import ir.karcook.Base.BasePresnter;
import ir.karcook.Base.BaseView;

/**
 * Created by John Cale on 6/5/2018.
 */

public interface RegisterContract {



    interface presenter extends BasePresnter {
        Context getContext();
        void register(Map<String, Object> params);
        void requestForgetCode(Map<String, String> params);
    }

    interface view extends BaseView<presenter> {

        Context getContext();
        void registerSuccess();
        void registerFailed(String error);
        void userDisabled(String error);
        void requestVerifyCodeSuccess();
        void requestVerifyCodeFailed(String error);


    }
}
