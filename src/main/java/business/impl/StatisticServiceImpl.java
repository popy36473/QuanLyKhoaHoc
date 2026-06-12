package business.impl;

import business.IStatisticService;
import dao.IStatisticDAO;
import dao.impl.StatisticDAOImpl;
import model.Statistic;

import java.util.List;

public class StatisticServiceImpl implements IStatisticService {
    private IStatisticDAO statisticDAO = new StatisticDAOImpl();

    @Override
    public int countCourses() {
        return statisticDAO.countCourses();
    }

    @Override
    public int countStudents() {
        return statisticDAO.countStudents();
    }

    @Override
    public List<Statistic> countStudentsByCourse() {
        return statisticDAO.countStudentsByCourse();
    }

    @Override
    public List<Statistic> top5CoursesByStudents() {
        return statisticDAO.top5CoursesByStudents();
    }

    @Override
    public List<Statistic> coursesMoreThan10Students() {
        return statisticDAO.coursesMoreThan10Students();
    }
}