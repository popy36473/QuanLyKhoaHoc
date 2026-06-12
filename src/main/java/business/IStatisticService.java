package business;

import model.Statistic;
import java.util.List;

public interface IStatisticService {
    int countCourses();

    int countStudents();

    List<Statistic> countStudentsByCourse();

    List<Statistic> top5CoursesByStudents();

    List<Statistic> coursesMoreThan10Students();
}