package ir.karcook.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ErrorAPIModel {
    @SerializedName("errors")
    @Expose
    private Errors errors;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("detail")
    @Expose
    private String detail;
    @SerializedName("instance")
    @Expose
    private String instance;

    public Errors getErrors() {
        return errors;
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public static class Errors {

        @SerializedName("ErrorDetails")
        @Expose
        private List<String> errorDetails = null;

        public List<String> getErrorDetails() {
            return errorDetails;
        }

        public void setErrorDetails(List<String> errorDetails) {
            this.errorDetails = errorDetails;
        }

    }
}
