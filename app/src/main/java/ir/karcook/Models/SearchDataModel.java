package ir.karcook.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SearchDataModel {

    @SerializedName("course")
    @Expose
    private List<CourseModel> course = new ArrayList<>();
    @SerializedName("courseCount")
    @Expose
    private Integer courseCount;

    public List<CourseModel> getCourse() {
        return course;
    }

    public void setCourse(List<CourseModel> course) {
        this.course = course;
    }

    public Integer getCourseCount() {
        return courseCount;
    }

    public void setCourseCount(Integer courseCount) {
        this.courseCount = courseCount;
    }
}
