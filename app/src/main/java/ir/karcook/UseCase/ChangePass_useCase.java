package ir.karcook.UseCase;

import android.content.Context;

import java.util.Map;

import ir.karcook.Base.UseCase;
import ir.karcook.Models.LoginAPIModel;
import ir.karcook.Models.MessageAPIModel;
import ir.karcook.Repository.Ripo;

public class ChangePass_useCase implements UseCase<Map<String,String>, MessageAPIModel> {
    @Override
    public void execute(Map<String, String> parameter, CallBack<MessageAPIModel> callBack, Context context) {
        Ripo.getInstance(context).changePassword(parameter , callBack);
    }
}
