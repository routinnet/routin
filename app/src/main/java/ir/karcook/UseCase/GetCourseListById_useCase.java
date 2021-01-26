package ir.karcook.UseCase;

import android.content.Context;

import java.util.Map;

import ir.karcook.Base.UseCase;
import ir.karcook.Models.CourseDetailAPIModel;
import ir.karcook.Models.CourseListApiModel;
import ir.karcook.Repository.Ripo;

public class GetCourseListById_useCase implements UseCase<Integer, CourseListApiModel> {
    @Override
    public void execute(Integer id, CallBack<CourseListApiModel> callBack, Context context) {
        Ripo.getInstance(context).getCourseListById(id, callBack);
    }
}
