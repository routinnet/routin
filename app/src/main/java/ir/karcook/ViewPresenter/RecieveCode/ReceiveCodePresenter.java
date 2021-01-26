package ir.karcook.ViewPresenter.RecieveCode;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

import ir.karcook.Base.UseCase;
import ir.karcook.Models.LoginAPIModel;
import ir.karcook.Models.MessageAPIModel;
import ir.karcook.R;
import ir.karcook.Tools.G;
import ir.karcook.Tools.Shared_Prefrences;
import ir.karcook.UseCase.GetVerifyCode_useCase;
import ir.karcook.UseCase.VerifyCode_useCase;

public class ReceiveCodePresenter implements ReceiveCodeContract.presenter {
    private ReceiveCodeContract.view iv_ReceiveCode;
    private Context context;
    VerifyCode_useCase verifyCode_useCase;
    GetVerifyCode_useCase getVerifyCode_useCase;

    public ReceiveCodePresenter(ReceiveCodeContract.view iv_ReceiveCode, VerifyCode_useCase verifyCode_useCase
            , GetVerifyCode_useCase getVerifyCode_useCase) {
        this.iv_ReceiveCode = iv_ReceiveCode;
        setContext(iv_ReceiveCode.getContext());
        this.verifyCode_useCase = verifyCode_useCase;
        this.getVerifyCode_useCase = getVerifyCode_useCase;
    }


    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public Context getContext() {
        return this.context;
    }


    @Override
    public void activeAccount(String code, final String phoneNumber) {
        Map<String, Object> params = new HashMap<>();
        params.put("Username", G.getInstance().replaceFarsiNumber(phoneNumber));
        params.put("ConfirmCode", G.getInstance().replaceFarsiNumber(code));
        verifyCode_useCase.execute(params, new UseCase.CallBack<LoginAPIModel>() {
            @Override
            public void onSuccess(LoginAPIModel model) {
                SharedPreferences sp = Shared_Prefrences.getInstance(context).getSp();
                SharedPreferences.Editor editor = sp.edit();
                editor.putString(context.getResources().getString(R.string.token), model.getData().getAccessToken());
                editor.putBoolean(context.getResources().getString(R.string.logged), true);
                editor.apply();
                iv_ReceiveCode.accountActivated();

            }

            @Override
            public void onError(String error) {
                iv_ReceiveCode.failedActivation(error);
            }
        }, context);
    }

    @Override
    public void requestCode(String phone) {
        Map<String, String> params = new HashMap<>();
        params.put("Username", G.getInstance().replaceFarsiNumber(phone));
        getVerifyCode_useCase.execute(params, new UseCase.CallBack<MessageAPIModel>() {
            @Override
            public void onSuccess(MessageAPIModel response) {
                iv_ReceiveCode.resendSuccess();
            }

            @Override
            public void onError(String error) {
                iv_ReceiveCode.resendFailed(error);
            }
        }, context);
    }

}