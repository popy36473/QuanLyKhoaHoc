package app;

import dao.ICourseDAO;
import dao.impl.CourseDAOImpl;
import model.Course;
import presentation.MainView;
import utils.DBUtil;

import java.sql.Connection;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        MainView mainView = new MainView();
        mainView.showMenu();


    }
}
