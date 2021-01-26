package ir.karcook.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileAPIModel extends ErrorAPIModel {

    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("score")
    @Expose
    private Integer score;
    @SerializedName("introducingFriendsCode")
    @Expose
    private String introducingFriendsCode;
    @SerializedName("subscribeDay")
    @Expose
    private String subscribeDay;
    @SerializedName("hasSubscribe")
    @Expose
    private Boolean hasSubscribe;
    @SerializedName("age")
    @Expose
    private Integer age;
    @SerializedName("degreeOfEducation")
    @Expose
    private Integer degreeOfEducation;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getIntroducingFriendsCode() {
        return introducingFriendsCode;
    }

    public void setIntroducingFriendsCode(String introducingFriendsCode) {
        this.introducingFriendsCode = introducingFriendsCode;
    }

    public String getSubscribeDay() {
        return subscribeDay;
    }

    public void setSubscribeDay(String subscribeDay) {
        this.subscribeDay = subscribeDay;
    }

    public Boolean getHasSubscribe() {
        return hasSubscribe;
    }

    public void setHasSubscribe(Boolean hasSubscribe) {
        this.hasSubscribe = hasSubscribe;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getDegreeOfEducation() {
        return degreeOfEducation;
    }

    public void setDegreeOfEducation(Integer degreeOfEducation) {
        this.degreeOfEducation = degreeOfEducation;
    }
}
