package dao;

import model.Enrollment;

import java.util.List;

public interface IEnrollmentDAO {
    List<Enrollment> findAll();

    List<Enrollment> findByCourseId(int courseId);

    Enrollment findById(int id);

    boolean approve(int id);

    boolean deleteById(int id);




    boolean registerCourse(int studentId, int courseId);

    List<Enrollment> findByStudentId(int studentId);

    Enrollment findByStudentAndCourse(int studentId, int courseId);

    boolean cancelEnrollment(int enrollmentId);
}