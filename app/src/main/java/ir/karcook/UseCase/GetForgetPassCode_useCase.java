package ir.karcook.UseCase;

import android.content.Context;

import java.util.Map;

import ir.karcook.Base.UseCase;
import ir.karcook.Models.ForgetPassCodeApiModel;
import ir.karcook.Models.LoginAPIModel;
import ir.karcook.Repository.Ripo;

public class GetForgetPassCode_useCase implements UseCase<Map<String, String>, ForgetPassCodeApiModel> {
    @Override
    public void execute(Map<String, String> parameter, CallBack< ForgetPassCodeApiModel> callBack, Context context) {
        Ripo.getInstance(context).getForgetPassCode(parameter, callBack);

    }
}
