package ir.karcook.UseCase;

import android.content.Context;

import java.util.Map;
import ir.karcook.Base.UseCase;
import ir.karcook.Models.LoginAPIModel;
import ir.karcook.Repository.Ripo;

public class Login_useCase implements UseCase<Map<String,String>, LoginAPIModel> {
    @Override
    public void execute(Map<String, String> parameter, UseCase.CallBack<LoginAPIModel> callBack, Context context) {
        Ripo.getInstance(context).login(parameter , callBack);
    }
}
