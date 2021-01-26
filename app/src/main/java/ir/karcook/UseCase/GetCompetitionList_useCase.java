package ir.karcook.UseCase;

import android.content.Context;

import java.util.Map;

import ir.karcook.Base.UseCase;
import ir.karcook.Models.CompetitionListAPIModel;
import ir.karcook.Models.SubscribeAPIModel;
import ir.karcook.Repository.Ripo;

public class GetCompetitionList_useCase implements UseCase<Map<String, Integer>, CompetitionListAPIModel> {
    @Override
    public void execute(Map<String, Integer> parameter, CallBack<CompetitionListAPIModel> callBack, Context context) {
        Ripo.getInstance(context).getCompetitionList(parameter, callBack);
    }
}
