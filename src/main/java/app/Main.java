package app;

import business.IEnrollmentService;
import business.IStudentService;
import business.impl.EnrollmentServiceImpl;
import business.impl.StudentServiceImpl;
import dao.ICourseDAO;
import dao.IEnrollmentDAO;
import dao.IStudentDAO;
import dao.impl.CourseDAOImpl;
import dao.impl.EnrollmentDAOImpl;
import dao.impl.StudentDAOImpl;
import model.Course;
import model.Enrollment;
import model.Student;
import presentation.MainView;
import utils.DBUtil;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        MainView mainView = new MainView();
        mainView.showMenu();

    }
}
