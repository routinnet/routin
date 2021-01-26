package ir.karcook.UseCase;

import android.content.Context;

import java.util.Map;

import ir.karcook.Base.UseCase;
import ir.karcook.Models.HomeDataAPIModel;
import ir.karcook.Models.LoginAPIModel;
import ir.karcook.Repository.Ripo;

public class GetHomeData_useCase implements UseCase<Void, HomeDataAPIModel> {
    @Override
    public void execute(Void parameter, CallBack<HomeDataAPIModel> callBack, Context context) {
        Ripo.getInstance(context).getHomeData(parameter, callBack);
    }
}
