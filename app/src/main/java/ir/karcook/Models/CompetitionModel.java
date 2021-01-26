package ir.karcook.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompetitionModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("deleteState")
    @Expose
    private Boolean deleteState;
    @SerializedName("showStartDate")
    @Expose
    private String showStartDate;
    @SerializedName("showStartDate_persian")
    @Expose
    private String showStartDatePersian;
    @SerializedName("endDate")
    @Expose
    private String endDate;
    @SerializedName("endDate_persian")
    @Expose
    private String endDatePersian;
    @SerializedName("isEnable")
    @Expose
    private Boolean isEnable;
    @SerializedName("registerDate")
    @Expose
    private String registerDate;
    @SerializedName("documentId")
    @Expose
    private String documentId;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getDeleteState() {
        return deleteState;
    }

    public void setDeleteState(Boolean deleteState) {
        this.deleteState = deleteState;
    }

    public String getShowStartDate() {
        return showStartDate;
    }

    public void setShowStartDate(String showStartDate) {
        this.showStartDate = showStartDate;
    }

    public String getShowStartDatePersian() {
        return showStartDatePersian;
    }

    public void setShowStartDatePersian(String showStartDatePersian) {
        this.showStartDatePersian = showStartDatePersian;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndDatePersian() {
        return endDatePersian;
    }

    public void setEndDatePersian(String endDatePersian) {
        this.endDatePersian = endDatePersian;
    }

    public Boolean getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Boolean isEnable) {
        this.isEnable = isEnable;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
}
