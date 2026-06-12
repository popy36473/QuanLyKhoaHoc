package business.impl;

import business.IEnrollmentService;
import dao.IEnrollmentDAO;
import dao.impl.EnrollmentDAOImpl;
import model.Enrollment;

import java.util.List;

public class EnrollmentServiceImpl implements IEnrollmentService {
    private IEnrollmentDAO enrollmentDAO = new EnrollmentDAOImpl();

    @Override
    public List<Enrollment> findAll() {
        return enrollmentDAO.findAll();
    }

    @Override
    public List<Enrollment> findByCourseId(int courseId) {
        if (courseId <= 0) {
            return List.of();
        }

        return enrollmentDAO.findByCourseId(courseId);
    }

    @Override
    public Enrollment findById(int id) {
        if (id <= 0) {
            return null;
        }

        return enrollmentDAO.findById(id);
    }

    @Override
    public boolean approve(int id) {
        if (id <= 0) {
            return false;
        }

        Enrollment enrollment = enrollmentDAO.findById(id);

        if (enrollment == null) {
            return false;
        }

        if ("CONFIRMED".equals(enrollment.getStatus())) {
            return false;
        }

        return enrollmentDAO.approve(id);
    }

    @Override
    public boolean deleteById(int id) {
        if (id <= 0) {
            return false;
        }

        Enrollment enrollment = enrollmentDAO.findById(id);

        if (enrollment == null) {
            return false;
        }

        return enrollmentDAO.deleteById(id);
    }

    @Override
    public boolean registerCourse(int studentId, int courseId) {
        if (studentId <= 0 || courseId <= 0) {
            return false;
        }

        Enrollment oldEnrollment = enrollmentDAO.findByStudentAndCourse(studentId, courseId);

        if (oldEnrollment != null) {
            return false;
        }

        return enrollmentDAO.registerCourse(studentId, courseId);
    }


    @Override
    public List<Enrollment> findByStudentId(int studentId) {
        if (studentId <= 0) {
            return List.of();
        }

        return enrollmentDAO.findByStudentId(studentId);
    }

    @Override
    public Enrollment findByStudentAndCourse(int studentId, int courseId) {
        if (studentId <= 0 || courseId <= 0) {
            return null;
        }

        return enrollmentDAO.findByStudentAndCourse(studentId, courseId);
    }

    @Override
    public boolean cancelEnrollment(int enrollmentId) {
        if (enrollmentId <= 0) {
            return false;
        }

        Enrollment enrollment = enrollmentDAO.findById(enrollmentId);

        if (enrollment == null) {
            return false;
        }

        if (!"WAITING".equals(enrollment.getStatus())) {
            return false;
        }
        return enrollmentDAO.cancelEnrollment(enrollmentId);
    }
}