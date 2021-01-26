package ir.karcook.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CompetitionListAPIModel extends ErrorAPIModel {

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
        private List<CompetitionModel> competition = null;
        @SerializedName("competitionCount")
        @Expose
        private Integer competitionCount;

        public List<CompetitionModel> getCompetition() {
            return competition;
        }

        public void setCompetition(List<CompetitionModel> competition) {
            this.competition = competition;
        }

        public Integer getCompetitionCount() {
            return competitionCount;
        }

        public void setCompetitionCount(Integer competitionCount) {
            this.competitionCount = competitionCount;
        }

    }
}
