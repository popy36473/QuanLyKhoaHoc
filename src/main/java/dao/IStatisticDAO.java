package dao;

import model.Statistic;

import java.util.List;

public interface IStatisticDAO {
    int countCourses();

    int countStudents();

    List<Statistic> countStudentsByCourse();

    List<Statistic> top5CoursesByStudents();

    List<Statistic> coursesMoreThan10Students();
}