package ir.karcook.UseCase;

import android.content.Context;

import ir.karcook.Base.UseCase;
import ir.karcook.Models.CategoryApiModel;
import ir.karcook.Models.CompetitionQuestionsAPIModel;
import ir.karcook.Repository.Ripo;

public class GetCompetitionQuestions_useCase implements UseCase<Integer, CompetitionQuestionsAPIModel> {
    @Override
    public void execute(Integer id, CallBack<CompetitionQuestionsAPIModel> callBack, Context context) {
        Ripo.getInstance(context).getCompetitionQuestions(id, callBack);
    }
}
