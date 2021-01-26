package ir.karcook.UseCase;

import android.content.Context;

import ir.karcook.Base.UseCase;
import ir.karcook.Models.CategoryApiModel;
import ir.karcook.Models.HomeDataAPIModel;
import ir.karcook.Repository.Ripo;

public class GetCategoryList_useCase implements UseCase<Void, CategoryApiModel> {
    @Override
    public void execute(Void parameter, CallBack<CategoryApiModel> callBack, Context context) {
        Ripo.getInstance(context).getCategoryList(parameter, callBack);
    }
}
