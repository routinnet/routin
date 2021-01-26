package ir.karcook.ViewPresenter.ResetPassword;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import ir.karcook.Base.UseCase;
import ir.karcook.Models.ForgetPassCodeApiModel;
import ir.karcook.Models.MessageAPIModel;
import ir.karcook.Tools.G;
import ir.karcook.UseCase.GetForgetPassCode_useCase;
import ir.karcook.UseCase.GetVerifyCode_useCase;
import ir.karcook.UseCase.ResetPassword_useCase;

public class ResetPasswordPresenter implements ResetPasswordContract.presenter {
    private ResetPasswordContract.view iv_ResetPassword;
    private Context context;
    private ResetPassword_useCase resetPassword_useCase;
    GetForgetPassCode_useCase getForgetPassCode_useCase;

    public ResetPasswordPresenter(ResetPasswordContract.view iv_ResetPassword,  ResetPassword_useCase resetPassword_useCase,
                                  GetForgetPassCode_useCase getForgetPassCode_useCase) {
        this.iv_ResetPassword = iv_ResetPassword;
        setContext(iv_ResetPassword.getContext());
        this.resetPassword_useCase = resetPassword_useCase;
        this.getForgetPassCode_useCase = getForgetPassCode_useCase;

    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public Context getContext() {
        return this.context;
    }

    @Override
    public void resetPass(String phone, String code,String confirmCode, String password) {
        Map<String, Object> params = new HashMap<>();
        params.put("Code", code);
        params.put("CanfirmCode", Integer.parseInt(G.getInstance().replaceFarsiNumber(confirmCode)));
        params.put("UserName", G.getInstance().replaceFarsiNumber(phone));
        params.put("Password", G.getInstance().replaceFarsiNumber(password));
        params.put("ConfirmPassword", G.getInstance().replaceFarsiNumber(password));
        resetPassword_useCase.execute(params, new UseCase.CallBack<MessageAPIModel>() {
            @Override
            public void onSuccess(MessageAPIModel response) {
                iv_ResetPassword.resetPassSuccess();

            }

            @Override
            public void onError(String error) {
                iv_ResetPassword.resetPassFailed(error);
            }
        }, context);
    }

    @Override
    public void requestForgetCode(Map<String, String> params) {
        getForgetPassCode_useCase.execute(params, new UseCase.CallBack<ForgetPassCodeApiModel>() {
            @Override
            public void onSuccess(ForgetPassCodeApiModel model) {
                iv_ResetPassword.requestVerifyCodeSuccess();
            }

            @Override
            public void onError(String error) {
                iv_ResetPassword.requestVerifyCodeFailed(error);
            }
        }, context);
    }
}