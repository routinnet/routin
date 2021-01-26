package ir.karcook.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubCategoryModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("categoryId")
    @Expose
    private Integer categoryId;
    @SerializedName("courseType")
    @Expose
    private Integer courseType;
    @SerializedName("categoryTitle")
    @Expose
    private String categoryTitle;
    @SerializedName("courseCount")
    @Expose
    private Integer courseCount;
    @SerializedName("persianRegisterDate")
    @Expose
    private String persianRegisterDate;
    @SerializedName("courses")
    @Expose
    private Object courses;
    @SerializedName("relatedCourses")
    @Expose
    private Object relatedCourses;
    @SerializedName("document")
    @Expose
    private Object document;
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

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getCourseType() {
        return courseType;
    }

    public void setCourseType(Integer courseType) {
        this.courseType = courseType;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public Integer getCourseCount() {
        return courseCount;
    }

    public void setCourseCount(Integer courseCount) {
        this.courseCount = courseCount;
    }

    public String getPersianRegisterDate() {
        return persianRegisterDate;
    }

    public void setPersianRegisterDate(String persianRegisterDate) {
        this.persianRegisterDate = persianRegisterDate;
    }

    public Object getCourses() {
        return courses;
    }

    public void setCourses(Object courses) {
        this.courses = courses;
    }

    public Object getRelatedCourses() {
        return relatedCourses;
    }

    public void setRelatedCourses(Object relatedCourses) {
        this.relatedCourses = relatedCourses;
    }

    public Object getDocument() {
        return document;
    }

    public void setDocument(Object document) {
        this.document = document;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
}
