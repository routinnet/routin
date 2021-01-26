package ir.karcook.UseCase;

import android.content.Context;

import java.util.Map;

import ir.karcook.Base.UseCase;
import ir.karcook.Models.CourseDetailAPIModel;
import ir.karcook.Models.SearchAPIModel;
import ir.karcook.Models.SearchSendModel;
import ir.karcook.Repository.Ripo;

public class GetCourseDetail_useCase implements UseCase<Map<String , Integer>, CourseDetailAPIModel> {
    @Override
    public void execute(Map<String , Integer> parameter, CallBack<CourseDetailAPIModel> callBack, Context context) {
        Ripo.getInstance(context).getCourseDetail(parameter, callBack);
    }
}
