package ir.karcook.UseCase;

import android.content.Context;

import java.util.Map;

import ir.karcook.Base.UseCase;
import ir.karcook.Models.CourseListApiModel;
import ir.karcook.Models.SearchAPIModel;
import ir.karcook.Models.SearchSendModel;
import ir.karcook.Repository.Ripo;

public class DoSearch_courseId_useCase implements UseCase<Map<String, Object>, CourseListApiModel> {
    @Override
    public void execute(Map<String, Object> parameter, CallBack<CourseListApiModel> callBack, Context context) {
        Ripo.getInstance(context).searchFromTextAndCoursePackageId(parameter, callBack);
    }
}
