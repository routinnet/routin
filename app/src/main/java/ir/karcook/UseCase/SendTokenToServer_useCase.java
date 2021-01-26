package ir.karcook.UseCase;

import android.content.Context;

import java.util.Map;

import ir.karcook.Base.UseCase;
import ir.karcook.Models.CourseListApiModel;
import ir.karcook.Models.MessageAPIModel;
import ir.karcook.Repository.Ripo;


public class SendTokenToServer_useCase implements UseCase<String, MessageAPIModel> {
    @Override
    public void execute(String parameter, CallBack<MessageAPIModel> callBack, Context context) {
        Ripo.getInstance(context).sendTokenToServer(parameter , callBack);
    }
}
