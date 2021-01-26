package ir.karcook.UseCase;

import android.content.Context;
import android.content.Intent;

import java.util.Map;

import ir.karcook.Base.UseCase;
import ir.karcook.Models.CourseDetailAPIModel;
import ir.karcook.Models.SubCategoryApiModel;
import ir.karcook.Repository.Ripo;

public class GetSubCategory_useCase implements UseCase<Integer, SubCategoryApiModel> {
    @Override
    public void execute(Integer parameter, CallBack<SubCategoryApiModel> callBack, Context context) {
        Ripo.getInstance(context).getSubCategory(parameter, callBack);
    }
}
