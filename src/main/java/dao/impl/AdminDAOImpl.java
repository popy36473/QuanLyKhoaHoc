package dao.impl;

import dao.IAdminDAO;
import model.Admin;
import utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAOImpl implements IAdminDAO {


    @Override
    public Admin login(String username, String password) {
        Connection con;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        con = DBUtil.openConnection();
        try {
            pstmt = con.prepareStatement("SELECT id,username,password  " +
                                             "FROM admin " +
                                             "Where username = ? AND password = ? ");
            pstmt.setString(1,username);
            pstmt.setString(2,password);

            rs =pstmt.executeQuery();
                if(rs.next()){
                    Admin admin = new Admin();
                    admin.setId(rs.getInt("id"));
                    admin.setUsername(rs.getString("username"));
                    admin.setPassword(rs.getString("password"));
                    return admin;
                }

        } catch (SQLException e) {
            throw new RuntimeException("Lỗi đăng nhập",e);
        }finally {
            DBUtil.closeConnection(pstmt,rs,con);
        }
        return null;
    }
}
