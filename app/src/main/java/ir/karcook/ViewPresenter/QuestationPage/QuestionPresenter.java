package ir.karcook.ViewPresenter.QuestationPage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ir.karcook.Base.UseCase;
import ir.karcook.Models.AnswerModel;
import ir.karcook.Models.AnswerVideoAPIModel;
import ir.karcook.Models.CompetitionQuestionsAPIModel;
import ir.karcook.Models.MessageAPIModel;
import ir.karcook.R;
import ir.karcook.UseCase.AnswerQuestionCompetition_useCase;
import ir.karcook.UseCase.GetCompetitionQuestions_useCase;
import ir.karcook.UseCase.SaveUserCompetition_useCase;
import ir.karcook.ViewPresenter.ContentListPage.ContentListAdapter;
import ir.karcook.databinding.RowAnswerBinding;
import ir.karcook.databinding.RowContentBinding;

public class QuestionPresenter implements QuestionContract.presenter {
    private QuestionContract.view iv;
    private Context context;
    GetCompetitionQuestions_useCase getCompetitionQuestions_useCase;
    CompetitionQuestionsAPIModel model;
    QuestionListAdapter adapter;
    List<AnswerModel> answerModels;
    int positionOfQuestion = -1;
    AnswerQuestionCompetition_useCase answerQuestionCompetition_useCase;
    SaveUserCompetition_useCase saveUserCompetition_useCase;

    public QuestionPresenter(QuestionContract.view iv,
                             GetCompetitionQuestions_useCase getCompetitionQuestions_useCase,
                             AnswerQuestionCompetition_useCase answerQuestionCompetition_useCase,
                             SaveUserCompetition_useCase saveUserCompetition_useCase) {
        this.iv = iv;
        setContext(iv.getContext());
        this.getCompetitionQuestions_useCase = getCompetitionQuestions_useCase;
        this.answerQuestionCompetition_useCase = answerQuestionCompetition_useCase;
        this.saveUserCompetition_useCase = saveUserCompetition_useCase;
    }


    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public Context getContext() {
        return this.context;
    }

    public CompetitionQuestionsAPIModel getModel() {
        return model;
    }

    public void setModel(CompetitionQuestionsAPIModel model) {
        this.model = model;
    }

    public QuestionListAdapter getAdapter() {
        return adapter;
    }

    public int getPositionOfQuestion() {
        return positionOfQuestion;
    }

    public void setPositionOfQuestion(int positionOfQuestion) {
        this.positionOfQuestion = positionOfQuestion;
    }

    public void setAdapter(int positionOfQuestion) {
        this.positionOfQuestion = positionOfQuestion;
        answerModels = model.getData().getQuestionsModel().get(positionOfQuestion).getAnswerModel();
        this.adapter = new QuestionListAdapter(this);
    }

    @Override
    public void getQuestions(int id) {
        getCompetitionQuestions_useCase.execute(id, new UseCase.CallBack<CompetitionQuestionsAPIModel>() {
            @Override
            public void onSuccess(CompetitionQuestionsAPIModel response) {
                model = response;
                iv.getQuestionsSuccess(response);
            }

            @Override
            public void onError(String error) {
                iv.getQuestionsFailed(error);
            }
        }, context);
    }

    @Override
    public QuestionListAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        RowAnswerBinding binding = DataBindingUtil.inflate(inflater, R.layout.row_answer, parent, false);
        return new QuestionListAdapter.viewHolder(binding, this);

    }

    @Override
    public void onBindViewHolder(QuestionListAdapter.viewHolder holder, int position) {
        holder.binding.answerTitle.setText(answerModels.get(position).getTitle());
        holder.binding.number.setText("-" + (position + 1));
    }

    @Override
    public int getItemCount() {
        return model.getData().getQuestionsModel().get(positionOfQuestion).getAnswerModel().size();
    }

    @Override
    public void itemClicked(int adapterPosition) {
        iv.itemClicked(adapterPosition);
    }

    @Override
    public void answerQuestion(int answerId, int competitionId) {
        Map<String, Integer> params = new HashMap<>();
        params.put("AnswerId", answerId);
        params.put("competitionId", competitionId);
        answerQuestionCompetition_useCase.execute(params, new UseCase.CallBack<AnswerVideoAPIModel>() {
            @Override
            public void onSuccess(AnswerVideoAPIModel response) {
                if (response.getData())
                    iv.answerQuestionTrue();
                else
                    iv.answerQuestionFalse();
            }

            @Override
            public void onError(String error) {
                iv.answerQuestionFailed(error);
            }
        }, context);
    }

    @Override
    public void saveCompetition(int id) {
        saveUserCompetition_useCase.execute(String.valueOf(id), new UseCase.CallBack<MessageAPIModel>() {
            @Override
            public void onSuccess(MessageAPIModel response) {
                iv.saveCompetitionSuccess();
            }

            @Override
            public void onError(String error) {
                iv.saveCompetitionFailed(error);
            }
        }, context);
    }

}