package dao;

import model.Course;

import java.util.List;

public interface ICourseDAO {
    List<Course> findAll();

    Course findById(int id);
    boolean insert(Course course);

    boolean update(Course course);

    boolean deleteById(int id);

    List<Course> searchByName(String keyword);

    List<Course> sortByIdAsc();

    List<Course> sortByIdDesc();

    List<Course> sortByNameAsc();

    List<Course> sortByNameDesc();
}
