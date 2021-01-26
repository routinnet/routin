package ir.karcook.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import ir.karcook.Repository.ErrorHandling;

public class HomeDataAPIModel extends ErrorAPIModel {

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

        @SerializedName("primarySlide")
        @Expose
        private List<PrimarySlide> primarySlide = null;
        @SerializedName("adsSlide")
        @Expose
        private List<AdsSlide> adsSlide = null;
        @SerializedName("lastCoursePackage")
        @Expose
        private List<LastCoursePackage> lastCoursePackage = null;
        @SerializedName("lastCourse")
        @Expose
        private List<Course> lastCourse = null;
        @SerializedName("lastFreeCourses")
        @Expose
        private List<Course> lastFreeCourses = null;

        public List<PrimarySlide> getPrimarySlide() {
            return primarySlide;
        }

        public void setPrimarySlide(List<PrimarySlide> primarySlide) {
            this.primarySlide = primarySlide;
        }

        public List<AdsSlide> getAdsSlide() {
            return adsSlide;
        }

        public void setAdsSlide(List<AdsSlide> adsSlide) {
            this.adsSlide = adsSlide;
        }

        public List<LastCoursePackage> getLastCoursePackage() {
            return lastCoursePackage;
        }

        public void setLastCoursePackage(List<LastCoursePackage> lastCoursePackage) {
            this.lastCoursePackage = lastCoursePackage;
        }

        public List<Course> getLastCourse() {
            return lastCourse;
        }

        public void setLastCourse(List<Course> lastCourse) {
            this.lastCourse = lastCourse;
        }

        public List<Course> getLastFreeCourses() {
            return lastFreeCourses;
        }

        public void setLastFreeCourses(List<Course> lastFreeCourses) {
            this.lastFreeCourses = lastFreeCourses;
        }

    }

    public class PrimarySlide {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("sliderId")
        @Expose
        private Integer sliderId;
        @SerializedName("enabled")
        @Expose
        private Boolean enabled;
        @SerializedName("pictureId")
        @Expose
        private String pictureId;

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

        public Integer getSliderId() {
            return sliderId;
        }

        public void setSliderId(Integer sliderId) {
            this.sliderId = sliderId;
        }

        public Boolean getEnabled() {
            return enabled;
        }

        public void setEnabled(Boolean enabled) {
            this.enabled = enabled;
        }

        public String getPictureId() {
            return pictureId;
        }

        public void setPictureId(String pictureId) {
            this.pictureId = pictureId;
        }

    }

    public class AdsSlide {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("sliderId")
        @Expose
        private Integer sliderId;
        @SerializedName("enabled")
        @Expose
        private Boolean enabled;
        @SerializedName("pictureId")
        @Expose
        private String pictureId;
        @SerializedName("priority")
        @Expose
        private Integer priority;
        @SerializedName("link")
        @Expose
        private String link;

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

        public Integer getSliderId() {
            return sliderId;
        }

        public void setSliderId(Integer sliderId) {
            this.sliderId = sliderId;
        }

        public Boolean getEnabled() {
            return enabled;
        }

        public void setEnabled(Boolean enabled) {
            this.enabled = enabled;
        }

        public String getPictureId() {
            return pictureId;
        }

        public void setPictureId(String pictureId) {
            this.pictureId = pictureId;
        }

        public Integer getPriority() {
            return priority;
        }

        public void setPriority(Integer priority) {
            this.priority = priority;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }
    }

    public class LastCoursePackage {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("categoryId")
        @Expose
        private Integer categoryId;
        @SerializedName("persianRegisterDate")
        @Expose
        private String persianRegisterDate;
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

        public Integer getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(Integer categoryId) {
            this.categoryId = categoryId;
        }

        public String getPersianRegisterDate() {
            return persianRegisterDate;
        }

        public void setPersianRegisterDate(String persianRegisterDate) {
            this.persianRegisterDate = persianRegisterDate;
        }

        public String getDocumentId() {
            return documentId;
        }

        public void setDocumentId(String documentId) {
            this.documentId = documentId;
        }

    }

    public class Course {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("fileType")
        @Expose
        private Integer fileType;
        @SerializedName("duration")
        @Expose
        private Integer duration;
        @SerializedName("coursePackageId")
        @Expose
        private Integer coursePackageId;
        @SerializedName("coursePackageTitle")
        @Expose
        private String coursePackageTitle;
        @SerializedName("fileSize")
        @Expose
        private String fileSize;
        @SerializedName("downloadCount")
        @Expose
        private Integer downloadCount;
        @SerializedName("isFree")
        @Expose
        private Boolean isFree;
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

        public String getFileSize() {
            return fileSize;
        }

        public void setFileSize(String fileSize) {
            this.fileSize = fileSize;
        }

        public String getDocumentId() {
            return documentId;
        }

        public void setDocumentId(String documentId) {
            this.documentId = documentId;
        }
    }
}
