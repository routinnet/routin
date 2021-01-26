package ir.karcook.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForgetPassCodeApiModel extends ErrorAPIModel {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private String data;
    @SerializedName("showMessage")
    @Expose
    private Boolean showMessage;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Boolean getShowMessage() {
        return showMessage;
    }

    public void setShowMessage(Boolean showMessage) {
        this.showMessage = showMessage;
    }
}
