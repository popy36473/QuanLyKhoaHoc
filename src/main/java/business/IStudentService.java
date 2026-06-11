package business;

import model.Student;

import java.util.List;

public interface IStudentService {
    Student login(String email, String password);

    List<Student> findAll();

    Student findById(int id);

    boolean insert(Student student);

    boolean update(Student student);

    boolean deleteById(int id);

    List<Student> search(String keyword);

    List<Student> sortByIdAsc();

    List<Student> sortByIdDesc();

    List<Student> sortByNameAsc();

    List<Student> sortByNameDesc();
}