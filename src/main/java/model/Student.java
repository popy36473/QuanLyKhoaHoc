package model;

import java.time.LocalDate;

public class Student {
    private Integer id;
    private String name;
    private LocalDate dob;
    private String email;
    private String sex;
    private String phone;
    private String password;
    private LocalDate createdAt;

    public Student() {
    }

    public Student(Integer id, String name, LocalDate dob, String email, String sex, String phone, String password, LocalDate createdAt) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.email = email;
        this.sex = sex;
        this.phone = phone;
        this.password = password;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        String gender = "1".equals(sex) ? "Nam" : "Nu";

        return "ID: " + id +
                " | Ten hoc vien: " + name +
                " | Ngay sinh: " + dob +
                " | Email: " + email +
                " | Gioi tinh: " + gender +
                " | SDT: " + phone +
                " | Ngay tao: " + createdAt;
    }
}
