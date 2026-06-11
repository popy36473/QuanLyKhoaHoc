package model;

import java.time.LocalDateTime;

public class Enrollment {
    private Integer id;
    private Integer studentId;
    private String studentName;
    private String studentEmail;

    private Integer courseId;
    private String courseName;

    private LocalDateTime registeredAt;
    private String status;

    public Enrollment() {
    }

    public Enrollment(Integer id, Integer studentId, String studentName, String studentEmail,
                      Integer courseId, String courseName, LocalDateTime registeredAt, String status) {
        this.id = id;
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.courseId = courseId;
        this.courseName = courseName;
        this.registeredAt = registeredAt;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
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

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(LocalDateTime registeredAt) {
        this.registeredAt = registeredAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}