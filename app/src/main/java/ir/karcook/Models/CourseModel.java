package ir.karcook.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CourseModel implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("fileUrl")
    @Expose
    private String fileUrl;
    @SerializedName("fileType")
    @Expose
    private Integer fileType;
    @SerializedName("duration")
    @Expose
    private Integer duration;
    @SerializedName("fileSize")
    @Expose
    private String fileSize;
    @SerializedName("coursePackageId")
    @Expose
    private Integer coursePackageId;
    @SerializedName("coursePackageTitle")
    @Expose
    private String coursePackageTitle;
    @SerializedName("downloadCount")
    @Expose
    private Integer downloadCount;
    @SerializedName("isFree")
    @Expose
    private Boolean isFree;
    @SerializedName("documentId")
    @Expose
    private String documentId;
    @SerializedName("document")
    @Expose
    private Object document;
    @SerializedName("relatedCourses")
    @Expose
    private List<CourseModel> relatedCourses;
    @SerializedName("hasCredit")
    @Expose
    private boolean hasCredit;
    @SerializedName("minimumAge")
    @Expose
    private Integer minimumAge;
    @SerializedName("question")
    @Expose
    private QuestionsModel questionsModel;


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

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public Integer getCoursePackageId() {
        return coursePackageId;
    }

    public void setCoursePackageId(Integer coursePackageId) {
        this.coursePackageId = coursePackageId;
    }

    public String getCoursePackageTitle() {
        return coursePackageTitle;
    }

    public void setCoursePackageTitle(String coursePackageTitle) {
        this.coursePackageTitle = coursePackageTitle;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }

    public Boolean getIsFree() {
        return isFree;
    }

    public void setIsFree(Boolean isFree) {
        this.isFree = isFree;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public Object getDocument() {
        return document;
    }

    public void setDocument(Object document) {
        this.document = document;
    }

    public List<CourseModel> getRelatedCourses() {
        return relatedCourses;
    }

    public void setRelatedCourses(List<CourseModel> relatedCourses) {
        this.relatedCourses = relatedCourses;
    }

    public boolean isHasCredit() {
        return hasCredit;
    }

    public void setHasCredit(boolean hasCredit) {
        this.hasCredit = hasCredit;
    }

    public Integer getMinimumAge() {
        return minimumAge;
    }

    public void setMinimumAge(Integer minimumAge) {
        this.minimumAge = minimumAge;
    }

    public QuestionsModel getQuestionsModel() {
        return questionsModel;
    }

    public void setQuestionsModel(QuestionsModel questionsModel) {
        this.questionsModel = questionsModel;
    }
}