package dao.impl;

import dao.ICourseDAO;
import model.Course;
import utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAOImpl implements ICourseDAO {

    private Course mapResultSetToCourse(ResultSet resultSet) throws SQLException{
        Course course = new Course();
        course.setId(resultSet.getInt("id"));
        course.setName(resultSet.getString("name"));
        course.setDuration(resultSet.getInt("duration"));
        course.setInstructor(resultSet.getString("instructor"));
        course.setCreatedAt(resultSet.getDate("created_at").toLocalDate());

        return course;
    }


    @Override
    public List<Course> findAll() {
        List<Course> courses = new ArrayList<>();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String sql = "Select id,name,duration,instructor,created_at " +
                     "From course " +
                     "Order by id ";

        try {
            con = DBUtil.openConnection();
            pstmt = con.prepareStatement(sql);
            rs =pstmt.executeQuery();

            while (rs.next()){
                Course course = new Course();
                course = mapResultSetToCourse(rs);
                courses.add(course);
            }
        }
         catch (SQLException e) {
            throw new RuntimeException("Loi lay danh sach khoa hoc",e);
        }finally {
            DBUtil.closeConnection(pstmt,rs,con);
        }

        return courses;
    }

    @Override
    public Course findById(int id) {
        String sql = """
            SELECT id, name, duration, instructor, created_at
            FROM course
            WHERE id = ?
            """;

        Connection con;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        con=DBUtil.openConnection();
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,id);
            rs=pstmt.executeQuery();
            if (rs.next()){
                return mapResultSetToCourse(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi tìm khóa học theo id",e);
        }finally {
            DBUtil.closeConnection(pstmt,rs,con);
        }
        return null;
    }

    @Override
    public boolean insert(Course course) {
        String sql = """
            INSERT INTO course(name, duration, instructor)
            VALUES (?, ?, ?)
            """;
        Connection con;
        PreparedStatement pstmt = null;

        con = DBUtil.openConnection();
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,course.getName());
            pstmt.setInt(2,course.getDuration());
            pstmt.setString(3,course.getInstructor());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException( "Lỗi thêm khóa học",e);
        }finally {
            DBUtil.closeConnection(pstmt,null,con);
        }
    }

    @Override
    public boolean update(Course course) {
        String sql = """
                Update course
                set name= ?,duration = ?, instructor = ?
                where id = ?
                """;

        Connection con;
        PreparedStatement pstmt = null;

        con = DBUtil.openConnection();
        try {
            pstmt = con.prepareStatement(sql);

            pstmt.setString(1,course.getName());
            pstmt.setInt(2,course.getDuration());
            pstmt.setString(3,course.getInstructor());
            pstmt.setInt(4,course.getId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi ud khoa hoc",e);
        }finally {
            DBUtil.closeConnection(pstmt,null,con);
        }

    }

    @Override
    public boolean deleteById(int id) {
        String sql = """
                delete from course
                where id = ?
                """;

        Connection con = DBUtil.openConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,id);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Loi delete khoa hoc",e);
        }finally {
            DBUtil.closeConnection(pstmt,null,con);
        }
    }

    @Override
    public List<Course> searchByName(String keyword) {
        List<Course> courses = new ArrayList<>();
        String sql = """
            SELECT id, name, duration, instructor, created_at
            FROM course
            WHERE LOWER(name) LIKE LOWER(?)
            ORDER BY id
            """;
        Connection con = DBUtil.openConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,"%"+keyword+"%");
            rs = pstmt.executeQuery();
            while(rs.next()){
                Course course = mapResultSetToCourse(rs);
                courses.add(course);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi ko tìm thấy khóa học",e);
        }finally {
            DBUtil.closeConnection(pstmt,rs,con);
        }
        return courses;
    }


    private List<Course> sort(String column, String direction){
        List<Course> courses = new ArrayList<>();
        String sql = "select id,name,duration,instructor,created_at " +
                     "from course order by " + column +" "+ direction;

        Connection con;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        con =DBUtil.openConnection();
        try {
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while(rs.next()){
                Course course = mapResultSetToCourse(rs);
                courses.add(course);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DBUtil.closeConnection(pstmt,rs,con);
        }
        return courses;
    }

    @Override
    public List<Course> sortByIdAsc() {
        return sort("id","asc");
    }

    @Override
    public List<Course> sortByIdDesc() {
        return sort("id","desc");
    }

    @Override
    public List<Course> sortByNameAsc() {
        return sort("name","asc");
    }

    @Override
    public List<Course> sortByNameDesc() {
        return sort("name","desc");
    }
}
