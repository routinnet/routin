package ir.karcook.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class QuestionsModel implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("competitionId")
    @Expose
    private Integer competitionId;
    @SerializedName("score")
    @Expose
    private Integer score;
    @SerializedName("isCorrect")
    @Expose
    private Object isCorrect;
    @SerializedName("timeToAnswer")
    @Expose
    private Integer timeToAnswer;
    @SerializedName("allAnswerCount")
    @Expose
    private Integer allAnswerCount;
    @SerializedName("answers")
    @Expose
    private Object answers;
    @SerializedName("userAnswer")
    @Expose
    private Integer userAnswer;
    @SerializedName("documentId")
    @Expose
    private String documentId;
    @SerializedName("document")
    @Expose
    private String document;
    @SerializedName("questionAnswer")
    @Expose
    private List<AnswerModel> answerModel = null;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(Integer competitionId) {
        this.competitionId = competitionId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Object getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Object isCorrect) {
        this.isCorrect = isCorrect;
    }

    public Integer getTimeToAnswer() {
        return timeToAnswer;
    }

    public void setTimeToAnswer(Integer timeToAnswer) {
        this.timeToAnswer = timeToAnswer;
    }

    public Integer getAllAnswerCount() {
        return allAnswerCount;
    }

    public void setAllAnswerCount(Integer allAnswerCount) {
        this.allAnswerCount = allAnswerCount;
    }

    public Object getAnswers() {
        return answers;
    }

    public void setAnswers(Object answers) {
        this.answers = answers;
    }

    public Integer getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(Integer userAnswer) {
        this.userAnswer = userAnswer;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public List<AnswerModel> getAnswerModel() {
        return answerModel;
    }

    public void setAnswerModel(List<AnswerModel> answerModel) {
        this.answerModel = answerModel;
    }
}
