package ir.karcook.UseCase;

import android.content.Context;

import java.util.Map;

import ir.karcook.Base.UseCase;
import ir.karcook.Models.AnswerVideoAPIModel;
import ir.karcook.Repository.Ripo;

public class AnswerQuestionCompetition_useCase implements UseCase<Map<String, Integer>, AnswerVideoAPIModel> {
    @Override
    public void execute(Map<String, Integer> parameter, CallBack<AnswerVideoAPIModel> callBack, Context context) {
        Ripo.getInstance(context).answerQuestion(parameter, callBack);
    }
}
