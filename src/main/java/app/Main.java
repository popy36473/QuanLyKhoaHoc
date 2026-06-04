package app;

import utils.DBUtil;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Connection con;

        con = DBUtil.openConnection();
        if (con != null){
            System.out.println("Kết nối thành công");
        }
    }
}
