package ir.karcook.UseCase;

import android.content.Context;

import java.util.Map;

import ir.karcook.Base.UseCase;
import ir.karcook.Models.CategoryApiModel;
import ir.karcook.Models.CreateFactorIdAPIModel;
import ir.karcook.Repository.Ripo;

public class CreateFactorId_useCase implements UseCase<Map<String, Integer>, CreateFactorIdAPIModel> {
    @Override
    public void execute(Map<String, Integer> parameter, CallBack<CreateFactorIdAPIModel> callBack, Context context) {
        Ripo.getInstance(context).createFactorId(parameter, callBack);
    }
}
