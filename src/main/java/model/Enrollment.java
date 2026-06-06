package model;

import java.time.LocalDateTime;

public class Enrollment {
    private int id;
    private int student_id;
    private int course_id;
    private LocalDateTime registeredAt;
    private String status;

    public Enrollment() {
    }

    public Enrollment(int id, int student_id, int course_id, LocalDateTime registeredAt, String status) {
        this.id = id;
        this.student_id = student_id;
        this.course_id = course_id;
        this.registeredAt = registeredAt;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
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
