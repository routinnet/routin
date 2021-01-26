package ir.karcook.UseCase;

import android.content.Context;

import ir.karcook.Base.UseCase;
import ir.karcook.Models.CategoryApiModel;
import ir.karcook.Models.SubscribeAPIModel;
import ir.karcook.Repository.Ripo;

public class GetSubscribeList_useCase implements UseCase<Void, SubscribeAPIModel> {
    @Override
    public void execute(Void parameter, CallBack<SubscribeAPIModel> callBack, Context context) {
        Ripo.getInstance(context).getSubscribeList(parameter, callBack);
    }
}
