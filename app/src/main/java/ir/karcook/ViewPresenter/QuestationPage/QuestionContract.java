package ir.karcook.ViewPresenter.QuestationPage;

import android.content.Context;
import android.view.ViewGroup;

import ir.karcook.Base.BasePresnter;
import ir.karcook.Base.BaseView;
import ir.karcook.Models.CompetitionQuestionsAPIModel;

public interface QuestionContract {

    interface presenter extends BasePresnter {
        Context getContext();

        void getQuestions(int id);

        QuestionListAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType);

        void onBindViewHolder(QuestionListAdapter.viewHolder holder, int position);

        int getItemCount();

        void itemClicked(int adapterPosition);

        void answerQuestion(int answerId, int competitionId);

        void saveCompetition(int id);
    }

    interface view extends BaseView<presenter> {

        Context getContext();

        void getQuestionsSuccess(CompetitionQuestionsAPIModel model);

        void getQuestionsFailed(String error);

        void itemClicked(int adapterPosition);

        void answerQuestionTrue();

        void answerQuestionFalse();

        void answerQuestionFailed(String error);

        void saveCompetitionSuccess();

        void saveCompetitionFailed(String error);
    }

}