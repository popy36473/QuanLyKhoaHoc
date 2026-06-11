package dao.impl;

import dao.IStudentDAO;
import model.Student;
import utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl implements IStudentDAO {
    private Student mapResultSetToStudent(ResultSet rs) throws SQLException {
        Student student = new Student();
        student.setId(rs.getInt("id"));
        student.setName(rs.getString("name"));
        student.setDob(rs.getDate("dob").toLocalDate());
        student.setEmail(rs.getString("email"));
        student.setSex(rs.getString("sex"));
        student.setPhone(rs.getString("phone"));
        student.setPassword(rs.getString("password"));

        Date createdAt = rs.getDate("created_at");
        if (createdAt != null) {
            student.setCreatedAt(createdAt.toLocalDate());
        }
        return student;

    }

    @Override
    public Student login(String email, String password) {
        String sql = """
                select id, name, dob, email, sex, phone, password, created_at
                from student
                where email = ? and password = ?
                """;
        Connection con;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        con = DBUtil.openConnection();
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,email);
            pstmt.setString(2,password);

            rs=pstmt.executeQuery();

            if (rs.next()){
                return mapResultSetToStudent(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi đăng nhập học viên",e);
        }finally {
            DBUtil.closeConnection(pstmt,rs,con);
        }
        return null;
    }

    @Override
    public List<Student> findAll() {
        List<Student> students = new ArrayList<>();
        String sql = """
                select id, name, dob, email, sex, phone, password, created_at
                from student
                order by id
                """;

        Connection con ;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        con = DBUtil.openConnection();
        try {
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()){
                Student student = mapResultSetToStudent(rs);
                students.add(student);
            }
        } catch (SQLException e) {
            throw new RuntimeException(" Lỗi findall Student ",e);
        }finally {
            DBUtil.closeConnection(pstmt,rs,con);
        }
        return students;
    }

    @Override
    public Student findById(int id) {
        Connection con;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = """
                select id, name, dob, email, sex, phone, password, created_at
                from student 
                where id = ?
                """;
        con = DBUtil.openConnection();
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,id);
            rs = pstmt.executeQuery();
            if (rs.next()){
                return  mapResultSetToStudent(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DBUtil.closeConnection(pstmt,rs,con);
        }
        return null;
    }




    @Override
    public boolean insert(Student student) {
        String sql = """
                INSERT INTO student(name, dob, email, sex, phone, password)
                VALUES (?, ?, ?, CAST(? AS BIT), ?, ?) 
                """;
        Connection con = DBUtil.openConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, student.getName());
            pstmt.setDate(2, Date.valueOf(student.getDob()));
            pstmt.setString(3, student.getEmail());
            pstmt.setString(4, student.getSex());
            pstmt.setString(5, student.getPhone());
            pstmt.setString(6, student.getPassword());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi thêm học viên", e);
        }finally {
            DBUtil.closeConnection(pstmt,null,con);
        }
    }

    @Override
    public boolean update(Student student) {
        String sql = """
            UPDATE student
            SET name = ?, dob = ?, email = ?, sex = CAST(? AS BIT), phone = ?, password = ?
            WHERE id = ?
            """;

        Connection con = DBUtil.openConnection();
        PreparedStatement pstmt = null;

        try {
            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, student.getName());
            pstmt.setDate(2, Date.valueOf(student.getDob()));
            pstmt.setString(3, student.getEmail());
            pstmt.setString(4, student.getSex());
            pstmt.setString(5, student.getPhone());
            pstmt.setString(6, student.getPassword());
            pstmt.setInt(7, student.getId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Lỗi cập nhật học viên", e);
        } finally {
            DBUtil.closeConnection(pstmt, null, con);
        }
    }

    @Override
    public boolean deleteById(int id) {
        String sql = """
            DELETE FROM student
            WHERE id = ?
            """;

        Connection con = DBUtil.openConnection();
        PreparedStatement pstmt = null;

        try {
            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, id);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Lỗi xóa học viên", e);
        } finally {
            DBUtil.closeConnection(pstmt, null, con);
        }
    }

    @Override
    public List<Student> search(String keyword) {
        List<Student> students = new ArrayList<>();

        String sql = """
            SELECT id, name, dob, email, sex, phone, password, created_at
            FROM student
            WHERE LOWER(name) LIKE LOWER(?)
               OR LOWER(email) LIKE LOWER(?)
               OR CAST(id AS TEXT) LIKE ?
            ORDER BY id
            """;

        Connection con = DBUtil.openConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = con.prepareStatement(sql);

            String key = "%" + keyword + "%";

            pstmt.setString(1, key);
            pstmt.setString(2, key);
            pstmt.setString(3, key);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                Student student = mapResultSetToStudent(rs);
                students.add(student);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Lỗi tìm kiếm học viên", e);
        } finally {
            DBUtil.closeConnection(pstmt, rs, con);
        }

        return students;
    }

    private List<Student> sort(String column, String direction) {
        List<Student> students = new ArrayList<>();

        String sql = "SELECT id, name, dob, email, sex, phone, password, created_at " +
                "FROM student " +
                "ORDER BY " + column + " " + direction;

        Connection con = DBUtil.openConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Student student = mapResultSetToStudent(rs);
                students.add(student);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Lỗi sắp xếp học viên", e);
        } finally {
            DBUtil.closeConnection(pstmt, rs, con);
        }

        return students;
    }

    @Override
    public List<Student> sortByIdAsc() {
        return sort("id", "ASC");
    }

    @Override
    public List<Student> sortByIdDesc() {
        return sort("id", "DESC");
    }

    @Override
    public List<Student> sortByNameAsc() {
        return sort("name", "ASC");
    }

    @Override
    public List<Student> sortByNameDesc() {
        return sort("name", "DESC");
    }
}
