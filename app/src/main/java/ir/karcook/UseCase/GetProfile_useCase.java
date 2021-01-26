package ir.karcook.UseCase;

import android.content.Context;

import ir.karcook.Base.UseCase;
import ir.karcook.Models.ProfileAPIModel;
import ir.karcook.Models.SubscribeAPIModel;
import ir.karcook.Repository.Ripo;

public class GetProfile_useCase implements UseCase<Void, ProfileAPIModel> {
    @Override
    public void execute(Void parameter, CallBack<ProfileAPIModel> callBack, Context context) {
        Ripo.getInstance(context).getProfile(parameter, callBack);
    }
}
