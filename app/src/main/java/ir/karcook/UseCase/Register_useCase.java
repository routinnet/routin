package ir.karcook.UseCase;

import android.content.Context;

import java.util.Map;

import ir.karcook.Base.UseCase;
import ir.karcook.Models.MessageAPIModel;
import ir.karcook.Repository.Ripo;


public class Register_useCase implements UseCase<Map<String, Object>, MessageAPIModel> {

    @Override
    public void execute(Map<String, Object> parameter, CallBack<MessageAPIModel> callBack, Context context) {
        Ripo.getInstance(context).register(parameter, callBack);

    }
}
