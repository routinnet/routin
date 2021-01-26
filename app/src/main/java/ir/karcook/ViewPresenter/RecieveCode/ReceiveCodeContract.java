package ir.karcook.ViewPresenter.RecieveCode;

import android.content.Context;

import ir.karcook.Base.BasePresnter;
import ir.karcook.Base.BaseView;

public interface ReceiveCodeContract {

    interface presenter extends BasePresnter {
        Context getContext();

        void activeAccount(String code, String phone);
        void requestCode(String phone);


    }

    interface view extends BaseView<presenter> {

        Context getContext();

        void resendSuccess();

        void resendFailed(String userMessage);

        void accountActivated();

        void failedActivation(String string);
    }

}