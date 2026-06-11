package dao.impl;

import dao.IEnrollmentDAO;
import model.Enrollment;
import utils.DBUtil;

import javax.naming.ldap.PagedResultsControl;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentDAOImpl implements IEnrollmentDAO {

    private Enrollment mapResultSetToEnrollment(ResultSet rs) throws SQLException {
        Enrollment enrollment = new Enrollment();

        enrollment.setId(rs.getInt("id"));
        enrollment.setStudentId(rs.getInt("student_id"));
        enrollment.setStudentName(rs.getString("student_name"));
        enrollment.setStudentEmail(rs.getString("student_email"));
        enrollment.setCourseId(rs.getInt("course_id"));
        enrollment.setCourseName(rs.getString("course_name"));

        Timestamp registeredAt = rs.getTimestamp("registered_at");
        if (registeredAt != null) {
            enrollment.setRegisteredAt(registeredAt.toLocalDateTime());
        }

        enrollment.setStatus(rs.getString("status"));

        return enrollment;
    }

    @Override
    public List<Enrollment> findAll() {
        List<Enrollment> enrollments = new ArrayList<>();
        String sql = """
                select
                    e.id, 
                    s.id  AS student_id,
                    s.name AS student_name,
                    s.email AS student_email,
                    c.id AS course_id,
                    c.name AS course_name,
                    e.registered_at,
                    e.status
                from enrollment e
                join student s ON e.student_id = s.id
                join course c ON e.course_id = c.id
                Order by c.id,e.id             
                """;

        Connection con;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        con = DBUtil.openConnection();
        try {
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()){
                Enrollment enrollment = mapResultSetToEnrollment(rs);
                enrollments.add(enrollment);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi hiển thị danh sách đăng ký khóa học",e);
        }finally {
            DBUtil.closeConnection(pstmt,rs,con);
        }
        return enrollments;
    }



    @Override
    public List<Enrollment> findByCourseId(int courseId) {
        List<Enrollment> enrollments = new ArrayList<>();

        String sql = """
                SELECT 
                    e.id,
                    s.id AS student_id,
                    s.name AS student_name,
                    s.email AS student_email,
                    c.id AS course_id,
                    c.name AS course_name,
                    e.registered_at,
                    e.status
                FROM enrollment e
                JOIN student s ON e.student_id = s.id
                JOIN course c ON e.course_id = c.id
                WHERE c.id = ?
                ORDER BY e.id
                """;

        Connection con = DBUtil.openConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, courseId);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                Enrollment enrollment = mapResultSetToEnrollment(rs);
                enrollments.add(enrollment);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Lỗi tìm danh sách đăng ký theo khóa học", e);
        } finally {
            DBUtil.closeConnection(pstmt, rs, con);
        }

        return enrollments;
    }

    @Override
    public Enrollment findById(int id) {
        String sql = """
                SELECT 
                    e.id,
                    s.id AS student_id,
                    s.name AS student_name,
                    s.email AS student_email,
                    c.id AS course_id,
                    c.name AS course_name,
                    e.registered_at,
                    e.status
                FROM enrollment e
                JOIN student s ON e.student_id = s.id
                JOIN course c ON e.course_id = c.id
                WHERE e.id = ?
                """;

        Connection con = DBUtil.openConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToEnrollment(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Lỗi tìm đăng ký khóa học theo id", e);
        } finally {
            DBUtil.closeConnection(pstmt, rs, con);
        }

        return null;
    }

    @Override
    public boolean approve(int id) {
        String sql = """
                UPDATE enrollment
                SET status = 'CONFIRMED'
                WHERE id = ?
                """;

        Connection con = DBUtil.openConnection();
        PreparedStatement pstmt = null;

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Lỗi duyệt đăng ký khóa học", e);
        } finally {
            DBUtil.closeConnection(pstmt, null, con);
        }
    }

    @Override
    public boolean deleteById(int id) {
        String sql = """
                DELETE FROM enrollment
                WHERE id = ?
                """;

        Connection con = DBUtil.openConnection();
        PreparedStatement pstmt = null;

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Lỗi xóa sinh viên khỏi khóa học", e);
        } finally {
            DBUtil.closeConnection(pstmt, null, con);
        }
    }
}
