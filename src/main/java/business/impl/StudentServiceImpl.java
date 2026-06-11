package business.impl;

import business.IStudentService;
import dao.IStudentDAO;
import dao.impl.StudentDAOImpl;
import model.Student;

import java.util.List;



public class StudentServiceImpl implements IStudentService {
    private IStudentDAO studentDAO = new StudentDAOImpl();

    private boolean isValidStudent(Student student) {
        if (student == null) {
            return false;
        }

        if (student.getName() == null || student.getName().trim().isEmpty()) {
            return false;
        }

        if (student.getDob() == null) {
            return false;
        }

        if (student.getEmail() == null || student.getEmail().trim().isEmpty()) {
            return false;
        }

        if (!student.getEmail().contains("@")) {
            return false;
        }

        if (student.getSex() == null ||
                (!student.getSex().equals("0") && !student.getSex().equals("1"))) {
            return false;
        }

        if (student.getPhone() == null || student.getPhone().trim().isEmpty()) {
            return false;
        }

        if (student.getPassword() == null || student.getPassword().trim().isEmpty()) {
            return false;
        }

        student.setName(student.getName().trim());
        student.setEmail(student.getEmail().trim());
        student.setSex(student.getSex().trim());
        student.setPhone(student.getPhone().trim());
        student.setPassword(student.getPassword().trim());

        return true;
    }


    @Override
    public Student login(String email, String password) {
        if (email == null || email.trim().isEmpty()){
            return null;
        }
        if (password == null || password.trim().isEmpty()) {
            return null;
        }
        return studentDAO.login(email.trim(),password.trim());
    }

    @Override
    public List<Student> findAll() {
        return studentDAO.findAll();
    }

    @Override
    public Student findById(int id) {
        if (id <= 0){
            return null;
        }
        return studentDAO.findById(id);
    }

    @Override
    public boolean insert(Student student) {
        if (!isValidStudent(student)){
            return false;
        }
        return studentDAO.insert(student);
    }

    @Override
    public boolean update(Student student) {
        if (student == null || student.getId() == null || student.getId() <= 0) {
            return false;
        }
        if (!isValidStudent(student)){
            return false;
        }
        return studentDAO.update(student);
    }

    @Override
    public boolean deleteById(int id) {
        if (id <= 0){
            return false;
        }
        return studentDAO.deleteById(id);
    }

    @Override
    public List<Student> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()){
            return findAll();
        }
        return studentDAO.search(keyword.trim());
    }

    @Override
    public List<Student> sortByIdAsc() {
        return studentDAO.sortByIdAsc();
    }

    @Override
    public List<Student> sortByIdDesc() {
        return studentDAO.sortByIdDesc();
    }

    @Override
    public List<Student> sortByNameAsc() {
        return studentDAO.sortByNameAsc();
    }

    @Override
    public List<Student> sortByNameDesc() {
        return studentDAO.sortByNameDesc();
    }
}
