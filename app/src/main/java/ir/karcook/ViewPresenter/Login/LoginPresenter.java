package ir.karcook.ViewPresenter.Login;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

import ir.karcook.Base.UseCase;
import ir.karcook.Models.ForgetPassCodeApiModel;
import ir.karcook.Models.LoginAPIModel;
import ir.karcook.Models.MessageAPIModel;
import ir.karcook.R;
import ir.karcook.Tools.G;
import ir.karcook.Tools.Shared_Prefrences;
import ir.karcook.UseCase.GetForgetPassCode_useCase;
import ir.karcook.UseCase.GetVerifyCode_useCase;
import ir.karcook.UseCase.Login_useCase;


public class LoginPresenter implements LoginContract.presenter {

    LoginContract.veiw iv_login;
    Context context;
    Login_useCase login_useCase;
    GetForgetPassCode_useCase getForgetPassCode_useCase;

    public LoginPresenter(LoginContract.veiw iv_login, Login_useCase login_useCase,
                          GetForgetPassCode_useCase getForgetPassCode_useCase) {
        this.iv_login = iv_login;
        context = iv_login.getContext();
        this.login_useCase = login_useCase;
        this.getForgetPassCode_useCase = getForgetPassCode_useCase;


    }

    public Context getContext() {
        return context;
    }

    @Override
    public void login(final String phone, String password) {

        Map<String, String> params = new HashMap<>();
        params.put("Username", G.getInstance().replaceFarsiNumber(phone));
        params.put("Password", G.getInstance().replaceFarsiNumber(password));

        login_useCase.execute(params, new UseCase.CallBack<LoginAPIModel>() {
            @Override
            public void onSuccess(LoginAPIModel model) {

                SharedPreferences sp = Shared_Prefrences.getInstance(context).getSp();
                SharedPreferences.Editor editor = sp.edit();
                editor.putString(context.getResources().getString(R.string.token), model.getData().getAccessToken());
                editor.putBoolean(context.getResources().getString(R.string.logged), true);
                editor.apply();
                iv_login.loginSuccess();
            }

            @Override
            public void onError(String error) {
                if (error.equals(context.getString(R.string.userLock)))
                    iv_login.userDisabled("شما قبلا ثبت نام کرده اید . برای دریافت رمز عبور از فراموشی رمز عبور استفاده کنید.");
                else
                    iv_login.loginFailed(error);
            }
        }, context);
    }

    @Override
    public void requestForgetCode(Map<String, String> params) {
        getForgetPassCode_useCase.execute(params, new UseCase.CallBack<ForgetPassCodeApiModel>() {
            @Override
            public void onSuccess(ForgetPassCodeApiModel model) {
                iv_login.requestVerifyCodeSuccess(model.getData());

            }

            @Override
            public void onError(String error) {
                iv_login.requestVerifyCodeFailed(error);
            }
        }, context);
    }


}
