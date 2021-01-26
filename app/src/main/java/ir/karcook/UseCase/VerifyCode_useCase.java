package ir.karcook.UseCase;

import android.content.Context;

import java.util.Map;

import ir.karcook.Base.UseCase;
import ir.karcook.Models.LoginAPIModel;
import ir.karcook.Repository.Ripo;

public class VerifyCode_useCase implements UseCase<Map<String,Object>, LoginAPIModel> {
    @Override
    public void execute(Map<String, Object> parameter, CallBack<LoginAPIModel> callBack, Context context) {
        Ripo.getInstance(context).verifyCode(parameter,callBack);

    }
}
