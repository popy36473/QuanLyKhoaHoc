package dao;

import model.Enrollment;

import java.util.List;

public interface IEnrollmentDAO {
    List<Enrollment> findAll();

    List<Enrollment> findByCourseId(int courseId);

    Enrollment findById(int id);

    boolean approve(int id);

    boolean deleteById(int id);
}