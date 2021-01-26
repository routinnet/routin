package ir.karcook.ViewPresenter.Register;

import android.content.Context;

import java.util.Map;

import ir.karcook.Base.UseCase;
import ir.karcook.Models.ForgetPassCodeApiModel;
import ir.karcook.Models.MessageAPIModel;
import ir.karcook.UseCase.GetForgetPassCode_useCase;
import ir.karcook.UseCase.GetVerifyCode_useCase;
import ir.karcook.UseCase.Register_useCase;

public class RegisterPresenter implements RegisterContract.presenter {

    private RegisterContract.view iv_Register;
    private Context context;
    private Register_useCase register_useCase;
    private GetForgetPassCode_useCase getForgetPassCode_useCase;

    public RegisterPresenter(RegisterContract.view iv_Register,
                             Register_useCase register_useCase,
                             GetForgetPassCode_useCase getForgetPassCode_useCase) {
        this.iv_Register = iv_Register;
        setContext(iv_Register.getContext());
        this.register_useCase = register_useCase;
        this.getForgetPassCode_useCase = getForgetPassCode_useCase;
    }

    public void setContext(Context context) {
        this.context = context;
    }


    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public void register(Map<String, Object> params) {
        register_useCase.execute(params, new UseCase.CallBack<MessageAPIModel>() {
            @Override
            public void onSuccess(MessageAPIModel model) {
                iv_Register.registerSuccess();

            }

            @Override
            public void onError(String error) {
                if (error.equals("userLock")) {
                    iv_Register.userDisabled("شما قبلا ثبت نام کرده اید . برای دریافت رمز عبور از فراموشی رمز عبور استفاده کنید.");
                } else
                    iv_Register.registerFailed(error);
            }
        }, context);
    }

    @Override
    public void requestForgetCode(Map<String, String> params) {
        getForgetPassCode_useCase.execute(params, new UseCase.CallBack<ForgetPassCodeApiModel>() {
            @Override
            public void onSuccess(ForgetPassCodeApiModel model) {
                iv_Register.requestVerifyCodeSuccess();
            }

            @Override
            public void onError(String error) {
                iv_Register.requestVerifyCodeFailed(error);
            }
        }, context);
    }
}