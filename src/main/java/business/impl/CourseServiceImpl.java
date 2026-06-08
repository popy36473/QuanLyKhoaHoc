package business.impl;

import business.ICourseService;
import dao.ICourseDAO;
import dao.impl.CourseDAOImpl;
import model.Course;

import java.util.List;

public class CourseServiceImpl implements ICourseService {

    private ICourseDAO courseDAO = new CourseDAOImpl();

    @Override
    public List<Course> findAll() {
        return courseDAO.findAll();
    }

    @Override
    public Course findById(int id) {
        if (id <= 0) {
            return null;
        }
        return courseDAO.findById(id);
    }

    @Override
    public boolean insert(Course course) {
        if (!isValidCourse(course)) {
            return false;
        }
        return courseDAO.insert(course);
    }

    @Override
    public boolean update(Course course) {
        if (isValidCourse(course)) {
            return false;
        }
        return courseDAO.update(course);
    }

    @Override
    public boolean deleteById(int id) {
        if (id <= 0) {
            return false;
        }
        return courseDAO.deleteById(id);
    }

    @Override
    public List<Course> searchByName(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return findAll();
        }
        return courseDAO.searchByName(keyword);
    }

    @Override
    public List<Course> sortByIdAsc() {
        return courseDAO.sortByIdAsc();
    }

    @Override
    public List<Course> sortByIdDesc() {
        return courseDAO.sortByIdDesc();
    }

    @Override
    public List<Course> sortByNameAsc() {
        return courseDAO.sortByNameAsc();
    }

    @Override
    public List<Course> sortByNameDesc() {
        return courseDAO.sortByNameDesc();
    }

    private boolean isValidCourse(Course course) {
        if (course == null) {
            return false;
        }

        if (course.getName() == null || course.getName().trim().isEmpty()) {
            return false;
        }

        if (course.getDuration() <= 0) {
            return false;
        }

        if (course.getInstructor() == null || course.getInstructor().trim().isEmpty()) {
            return false;
        }

        course.setName(course.getName().trim());
        course.setInstructor(course.getInstructor().trim());

        return true;
    }
}
