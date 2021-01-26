package ir.karcook.ViewPresenter.ProfilePage;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

import ir.karcook.Base.UseCase;
import ir.karcook.Models.MessageAPIModel;
import ir.karcook.Models.ProfileAPIModel;
import ir.karcook.Tools.G;
import ir.karcook.UseCase.ChangeAge_useCase;
import ir.karcook.UseCase.ChangePass_useCase;
import ir.karcook.UseCase.GetProfile_useCase;
import ir.karcook.UseCase.GetVerifyCode_useCase;

public class ProfilePresenter implements ProfileContract.presenter {
    private ProfileContract.view iv;
    private Context context;
    GetProfile_useCase getProfile_useCase;
    ChangePass_useCase changePass_useCase;
    GetVerifyCode_useCase getVerifyCode_useCase;
    ChangeAge_useCase changeAge_useCase;

    public ProfilePresenter(ProfileContract.view iv, GetProfile_useCase getProfile_useCase,
                            ChangePass_useCase changePass_useCase,
                            GetVerifyCode_useCase getVerifyCode_useCase,
                            ChangeAge_useCase changeAge_useCase) {
        this.iv = iv;
        setContext(iv.getContext());
        this.getProfile_useCase = getProfile_useCase;
        this.changePass_useCase = changePass_useCase;
        this.getVerifyCode_useCase = getVerifyCode_useCase;
        this.changeAge_useCase = changeAge_useCase;
    }


    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public Context getContext() {
        return this.context;
    }

    @Override
    public void changePass(String oldPass, String newPass) {
        Map<String, String> params = new HashMap<>();
        params.put("OldPassword", G.getInstance().replaceFarsiNumber(oldPass));
        params.put("NewPassword", G.getInstance().replaceFarsiNumber(newPass));
        params.put("ConfirmPassword", G.getInstance().replaceFarsiNumber(newPass));
        changePass_useCase.execute(params, new UseCase.CallBack<MessageAPIModel>() {
            @Override
            public void onSuccess(MessageAPIModel response) {
                iv.changePassSuccess();
            }

            @Override
            public void onError(String error) {
                iv.changePassFailed(error);
            }
        }, context);
    }

    @Override
    public void getProfile() {
        getProfile_useCase.execute(null, new UseCase.CallBack<ProfileAPIModel>() {
            @Override
            public void onSuccess(ProfileAPIModel response) {
                iv.getProfileSuccess(response);
            }

            @Override
            public void onError(String error) {
                iv.getProfileFailed(error);
            }
        }, context);
    }

    @Override
    public void getVerifyCode(String phone) {
        Map<String, String> params = new HashMap<>();
        params.put("Username", G.getInstance().replaceFarsiNumber(phone));
        getVerifyCode_useCase.execute(params, new UseCase.CallBack<MessageAPIModel>() {
            @Override
            public void onSuccess(MessageAPIModel response) {
                iv.getVerifyCodeSuccess();
            }

            @Override
            public void onError(String error) {
                iv.getVerifyCodeFailed(error);
            }
        }, context);
    }

    @Override
    public void doChangeAge(Map<String, Integer> params) {
        changeAge_useCase.execute(params, new UseCase.CallBack<MessageAPIModel>() {
            @Override
            public void onSuccess(MessageAPIModel response) {
                iv.doChangeAgeSuccess();
            }

            @Override
            public void onError(String error) {
                iv.doChangeAgeFailed(error);
            }
        }, context);
    }

}