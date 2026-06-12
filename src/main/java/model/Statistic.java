package model;

public class Statistic {
    private Integer courseId;
    private String courseName;
    private Integer totalStudents;

    public Statistic() {
    }

    public Statistic(Integer courseId, String courseName, Integer totalStudents) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.totalStudents = totalStudents;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(Integer totalStudents) {
        this.totalStudents = totalStudents;
    }
}