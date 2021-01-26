package ir.karcook.UseCase;

import android.content.Context;

import java.util.Map;

import ir.karcook.Base.UseCase;
import ir.karcook.Models.MessageAPIModel;
import ir.karcook.Repository.Ripo;

public class ChangeAge_useCase implements UseCase<Map<String, Integer>, MessageAPIModel> {
    @Override
    public void execute(Map<String, Integer> parameter, CallBack<MessageAPIModel> callBack, Context context) {
        Ripo.getInstance(context).changeAge(parameter, callBack);
    }
}
