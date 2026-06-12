package presentation;

import business.ICourseService;
import business.IEnrollmentService;
import business.IStudentService;
import business.impl.CourseServiceImpl;
import business.impl.EnrollmentServiceImpl;
import business.impl.StudentServiceImpl;
import model.Course;
import model.Enrollment;
import model.Student;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class StudentAccountView {
    private final Scanner scanner = new Scanner(System.in);

    private final IStudentService studentService = new StudentServiceImpl();
    private final ICourseService courseService = new CourseServiceImpl();
    private final IEnrollmentService enrollmentService = new EnrollmentServiceImpl();

    private Student currentStudent;

    public void showMenu() {
        loginStudent();

        if (currentStudent == null) {
            return;
        }

        do {
            System.out.println("\n================ MENU HỌC VIÊN ================");
            System.out.println("1. Xem danh sách khóa học");
            System.out.println("2. Tìm kiếm khóa học theo tên");
            System.out.println("3. Đăng ký khóa học");
            System.out.println("4. Xem khóa học đã đăng ký");
            System.out.println("5. Sắp xếp khóa học đã đăng ký");
            System.out.println("6. Hủy đăng ký khóa học");
            System.out.println("7. Cập nhật mật khẩu");
            System.out.println("8. Đăng xuất");
            System.out.print("Chọn chức năng: ");

            int choice = inputInt();

            switch (choice) {
                case 1:
                    displayAllCourses();
                    break;
                case 2:
                    searchCourseByName();
                    break;
                case 3:
                    registerCourse();
                    break;
                case 4:
                    displayMyEnrollments();
                    break;
                case 5:
                    sortMyEnrollments();
                    break;
                case 6:
                    cancelEnrollment();
                    break;
                case 7:
                    updatePassword();
                    break;
                case 8:
                    System.out.println("Đăng xuất thành công.");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }

        } while (true);
    }

    private void loginStudent() {
        System.out.println("\n=============== ĐĂNG NHẬP HỌC VIÊN ===============");

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Mật khẩu: ");
        String password = scanner.nextLine();

        currentStudent = studentService.login(email, password);

        if (currentStudent == null) {
            System.out.println("Email hoặc mật khẩu không đúng.");
        } else {
            System.out.println("Đăng nhập thành công. Xin chào " + currentStudent.getName());
        }
    }

    private void displayAllCourses() {
        List<Course> courses = courseService.findAll();

        if (courses.isEmpty()) {
            System.out.println("Chưa có khóa học nào.");
            return;
        }

        printCourseTable(courses);
    }

    private void searchCourseByName() {
        System.out.print("Nhập tên khóa học cần tìm: ");
        String keyword = scanner.nextLine();

        List<Course> courses = courseService.searchByName(keyword);

        if (courses.isEmpty()) {
            System.out.println("Không tìm thấy khóa học.");
            return;
        }

        printCourseTable(courses);
    }

    private void registerCourse() {
        displayAllCourses();

        System.out.print("Nhập ID khóa học muốn đăng ký: ");
        int courseId = inputInt();

        Course course = courseService.findById(courseId);

        if (course == null) {
            System.out.println("Không tồn tại khóa học này.");
            return;
        }

        boolean result = enrollmentService.registerCourse(currentStudent.getId(), courseId);

        if (result) {
            System.out.println("Đăng ký khóa học thành công. Vui lòng chờ admin duyệt.");
        } else {
            System.out.println("Đăng ký thất bại. Có thể bạn đã đăng ký khóa học này rồi.");
        }
    }

    private void displayMyEnrollments() {
        List<Enrollment> enrollments = enrollmentService.findByStudentId(currentStudent.getId());

        if (enrollments.isEmpty()) {
            System.out.println("Bạn chưa đăng ký khóa học nào.");
            return;
        }

        printEnrollmentTable(enrollments);
    }

    private void sortMyEnrollments() {
        List<Enrollment> enrollments = enrollmentService.findByStudentId(currentStudent.getId());

        if (enrollments.isEmpty()) {
            System.out.println("Bạn chưa đăng ký khóa học nào.");
            return;
        }

        System.out.println("\n========== SẮP XẾP KHÓA HỌC ĐÃ ĐĂNG KÝ ==========");
        System.out.println("1. Theo tên khóa học tăng dần");
        System.out.println("2. Theo tên khóa học giảm dần");
        System.out.println("3. Theo ngày đăng ký mới nhất");
        System.out.println("4. Theo ngày đăng ký cũ nhất");
        System.out.print("Chọn kiểu sắp xếp: ");

        int choice = inputInt();

        switch (choice) {
            case 1:
                enrollments.sort(Comparator.comparing(Enrollment::getCourseName));
                break;
            case 2:
                enrollments.sort(Comparator.comparing(Enrollment::getCourseName).reversed());
                break;
            case 3:
                enrollments.sort(Comparator.comparing(Enrollment::getRegisteredAt).reversed());
                break;
            case 4:
                enrollments.sort(Comparator.comparing(Enrollment::getRegisteredAt));
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ.");
                return;
        }

        printEnrollmentTable(enrollments);
    }

    private void cancelEnrollment() {
        List<Enrollment> enrollments = enrollmentService.findByStudentId(currentStudent.getId());

        if (enrollments.isEmpty()) {
            System.out.println("Bạn chưa đăng ký khóa học nào.");
            return;
        }

        printEnrollmentTable(enrollments);

        System.out.print("Nhập ID đăng ký muốn hủy: ");
        int enrollmentId = inputInt();

        Enrollment selectedEnrollment = null;

        for (Enrollment enrollment : enrollments) {
            if (enrollment.getId() == enrollmentId) {
                selectedEnrollment = enrollment;
                break;
            }
        }

        if (selectedEnrollment == null) {
            System.out.println("ID đăng ký không thuộc tài khoản của bạn.");
            return;
        }

        if (!"WAITING".equals(selectedEnrollment.getStatus())) {
            System.out.println("Chỉ được hủy đăng ký khi trạng thái là WAITING.");
            return;
        }

        boolean result = enrollmentService.cancelEnrollment(enrollmentId);

        if (result) {
            System.out.println("Hủy đăng ký thành công.");
        } else {
            System.out.println("Hủy đăng ký thất bại.");
        }
    }

    private void updatePassword() {
        System.out.print("Nhập mật khẩu cũ: ");
        String oldPassword = scanner.nextLine();

        if (!oldPassword.equals(currentStudent.getPassword())) {
            System.out.println("Mật khẩu cũ không đúng.");
            return;
        }

        System.out.print("Nhập mật khẩu mới: ");
        String newPassword = scanner.nextLine();

        System.out.print("Nhập lại mật khẩu mới: ");
        String confirmPassword = scanner.nextLine();

        if (newPassword.isBlank()) {
            System.out.println("Mật khẩu mới không được để trống.");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            System.out.println("Mật khẩu nhập lại không khớp.");
            return;
        }

        currentStudent.setPassword(newPassword);

        boolean result = studentService.update(currentStudent);

        if (result) {
            System.out.println("Cập nhật mật khẩu thành công.");
        } else {
            System.out.println("Cập nhật mật khẩu thất bại.");
        }
    }

    private void printCourseTable(List<Course> courses) {
        System.out.println("\n================================ DANH SÁCH KHÓA HỌC ================================");
        System.out.printf("%-5s %-30s %-15s %-25s %-15s%n",
                "ID", "Tên khóa học", "Thời lượng", "Giảng viên", "Ngày tạo");
        System.out.println("-----------------------------------------------------------------------------------");

        for (Course course : courses) {
            System.out.printf("%-5d %-30s %-15d %-25s %-15s%n",
                    course.getId(),
                    course.getName(),
                    course.getDuration(),
                    course.getInstructor(),
                    course.getCreatedAt());
        }
    }

    private void printEnrollmentTable(List<Enrollment> enrollments) {
        System.out.println("\n========================== KHÓA HỌC ĐÃ ĐĂNG KÝ ==========================");
        System.out.printf("%-5s %-30s %-25s %-15s%n",
                "ID", "Tên khóa học", "Ngày đăng ký", "Trạng thái");
        System.out.println("-------------------------------------------------------------------------");

        for (Enrollment enrollment : enrollments) {
            System.out.printf("%-5d %-30s %-25s %-15s%n",
                    enrollment.getId(),
                    enrollment.getCourseName(),
                    enrollment.getRegisteredAt(),
                    enrollment.getStatus());
        }
    }

    private int inputInt() {
        while (true) {
            try {
                int number = Integer.parseInt(scanner.nextLine());
                return number;
            } catch (NumberFormatException e) {
                System.out.print("Vui lòng nhập số: ");
            }
        }
    }
}