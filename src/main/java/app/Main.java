package app;

import presentation.MainView;
import utils.DBUtil;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        MainView mainView = new MainView();
        mainView.showMenu();


    }
}
