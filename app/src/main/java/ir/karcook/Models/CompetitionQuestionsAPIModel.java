package ir.karcook.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CompetitionQuestionsAPIModel extends ErrorAPIModel {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("showMessage")
    @Expose
    private Boolean showMessage;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Boolean getShowMessage() {
        return showMessage;
    }

    public void setShowMessage(Boolean showMessage) {
        this.showMessage = showMessage;
    }

    public class Data {

        @SerializedName("competition")
        @Expose
        private CompetitionModel competition;
        @SerializedName("competitionQuestionViewModel")
        @Expose
        private Object competitionQuestionViewModel;
        @SerializedName("competitionQuestionListViewModel")
        @Expose
        private List<QuestionsModel> questionsModel = null;
        @SerializedName("competitionQuestionAnswerViewModel")
        @Expose
        private Object competitionQuestionAnswerViewModel;

        public CompetitionModel getCompetition() {
            return competition;
        }

        public void setCompetition(CompetitionModel competition) {
            this.competition = competition;
        }

        public Object getCompetitionQuestionViewModel() {
            return competitionQuestionViewModel;
        }

        public void setCompetitionQuestionViewModel(Object competitionQuestionViewModel) {
            this.competitionQuestionViewModel = competitionQuestionViewModel;
        }

        public List<QuestionsModel> getQuestionsModel() {
            return questionsModel;
        }

        public void setQuestionsModel(List<QuestionsModel> questionsModel) {
            this.questionsModel = questionsModel;
        }

        public Object getCompetitionQuestionAnswerViewModel() {
            return competitionQuestionAnswerViewModel;
        }

        public void setCompetitionQuestionAnswerViewModel(Object competitionQuestionAnswerViewModel) {
            this.competitionQuestionAnswerViewModel = competitionQuestionAnswerViewModel;
        }
    }
}
