package dao.impl;

import dao.IStatisticDAO;
import model.Statistic;
import utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StatisticDAOImpl implements IStatisticDAO {


    @Override
    public int countCourses() {
        String sql = """
            SELECT COUNT(*)
            FROM course
            """;

        Connection con = DBUtil.openConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Lỗi thống kê tổng số khóa học", e);
        } finally {
            DBUtil.closeConnection(pstmt, rs, con);
        }

        return 0;
    }

    @Override
    public int countStudents() {
        String sql = """
                select count(*) as total_students 
                from student
                """;
        Connection con;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        con =DBUtil.openConnection();
        try {
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs.next()){
                return rs.getInt("total_students");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi count student",e);
        }finally {
            DBUtil.closeConnection(pstmt,rs,con);
        }
        return 0;
    }

    private List<Statistic> getCourseStatistics(String sql) {
        List<Statistic> statistics = new ArrayList<>();

        Connection con = DBUtil.openConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Statistic statistic = new Statistic();

                statistic.setCourseId(rs.getInt("course_id"));
                statistic.setCourseName(rs.getString("course_name"));
                statistic.setTotalStudents(rs.getInt("total_students"));

                statistics.add(statistic);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Lỗi thống kê khóa học", e);
        } finally {
            DBUtil.closeConnection(pstmt, rs, con);
        }

        return statistics;
    }

    @Override
    public List<Statistic> countStudentsByCourse() {
        String sql = """
                SELECT
                    c.id AS course_id,
                    c.name AS course_name,
                    COUNT(e.student_id) AS total_students
                FROM course c
                LEFT JOIN enrollment e ON c.id = e.course_id
                GROUP BY c.id, c.name
                ORDER BY c.id
                """;

        return getCourseStatistics(sql);
    }

    @Override
    public List<Statistic> top5CoursesByStudents() {
        String sql = """
                SELECT
                    c.id AS course_id,
                    c.name AS course_name,
                    COUNT(e.student_id) AS total_students
                FROM course c
                LEFT JOIN enrollment e ON c.id = e.course_id
                GROUP BY c.id, c.name
                ORDER BY total_students DESC
                LIMIT 5
                """;

        return getCourseStatistics(sql);
    }

    @Override
    public List<Statistic> coursesMoreThan10Students() {
        String sql = """
                SELECT
                    c.id AS course_id,
                    c.name AS course_name,
                    COUNT(e.student_id) AS total_students
                FROM course c
                JOIN enrollment e ON c.id = e.course_id
                GROUP BY c.id, c.name
                HAVING COUNT(e.student_id) > 10
                ORDER BY total_students DESC
                """;

        return getCourseStatistics(sql);
    }
}
