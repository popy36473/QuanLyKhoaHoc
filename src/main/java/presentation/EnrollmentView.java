package presentation;

import business.IEnrollmentService;
import business.impl.EnrollmentServiceImpl;
import model.Enrollment;

import java.util.List;
import java.util.Scanner;

public class EnrollmentView {
    private Scanner sc = new Scanner(System.in);
    private IEnrollmentService enrollmentService = new EnrollmentServiceImpl();

    public void showMenu() {
        int choice;

        do {
            System.out.println("\n========== QUẢN LÝ ĐĂNG KÝ KHÓA HỌC ==========");
            System.out.println("1. Hiển thị tất cả danh sách đăng ký");
            System.out.println("2. Hiển thị sinh viên đăng ký theo khóa học");
            System.out.println("3. Duyệt sinh viên đăng ký khóa học");
            System.out.println("4. Xóa sinh viên khỏi khóa học");
            System.out.println("5. Quay lại menu Admin");
            System.out.print("Nhập lựa chọn: ");

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Bạn phải nhập số.");
                choice = -1;
            }

            switch (choice) {
                case 1:
                    displayAllEnrollments();
                    break;

                case 2:
                    displayEnrollmentsByCourse();
                    break;

                case 3:
                    approveEnrollment();
                    break;

                case 4:
                    deleteEnrollment();
                    break;

                case 5:
                    System.out.println("Quay lại menu Admin.");
                    break;

                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }

        } while (choice != 5);
    }

    private void displayAllEnrollments() {
        List<Enrollment> enrollments = enrollmentService.findAll();

        if (enrollments.isEmpty()) {
            System.out.println("Danh sách đăng ký khóa học trống.");
            return;
        }

        printEnrollmentTable(enrollments);
    }

    private void displayEnrollmentsByCourse() {
        System.out.println("\n===== HIỂN THỊ ĐĂNG KÝ THEO KHÓA HỌC =====");

        int courseId;
        try {
            System.out.print("Nhập id khóa học: ");
            courseId = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID khóa học phải là số.");
            return;
        }

        List<Enrollment> enrollments = enrollmentService.findByCourseId(courseId);

        if (enrollments.isEmpty()) {
            System.out.println("Không có sinh viên đăng ký khóa học này.");
            return;
        }

        printEnrollmentTable(enrollments);
    }

    private void approveEnrollment() {
        System.out.println("\n===== DUYỆT SINH VIÊN ĐĂNG KÝ KHÓA HỌC =====");

        int id;
        try {
            System.out.print("Nhập id đăng ký cần duyệt: ");
            id = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID đăng ký phải là số.");
            return;
        }

        Enrollment enrollment = enrollmentService.findById(id);

        if (enrollment == null) {
            System.out.println("Không tìm thấy đăng ký có id = " + id);
            return;
        }

        System.out.println("\nThông tin đăng ký cần duyệt:");
        printEnrollmentTable(List.of(enrollment));

        System.out.print("Bạn có chắc chắn muốn duyệt đăng ký này không? (Y/N): ");
        String confirm = sc.nextLine();

        if (confirm.equalsIgnoreCase("Y")) {
            boolean result = enrollmentService.approve(id);

            if (result) {
                System.out.println("Duyệt đăng ký khóa học thành công.");
            } else {
                System.out.println("Duyệt thất bại. Đăng ký có thể đã được duyệt hoặc dữ liệu không hợp lệ.");
            }
        } else {
            System.out.println("Đã hủy thao tác duyệt.");
        }
    }

    private void deleteEnrollment() {
        System.out.println("\n===== XÓA SINH VIÊN KHỎI KHÓA HỌC =====");

        int id;
        try {
            System.out.print("Nhập id đăng ký cần xóa: ");
            id = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID đăng ký phải là số.");
            return;
        }

        Enrollment enrollment = enrollmentService.findById(id);

        if (enrollment == null) {
            System.out.println("Không tìm thấy đăng ký có id = " + id);
            return;
        }

        System.out.println("\nThông tin đăng ký cần xóa:");
        printEnrollmentTable(List.of(enrollment));

        System.out.print("Bạn có chắc chắn muốn xóa sinh viên khỏi khóa học này không? (Y/N): ");
        String confirm = sc.nextLine();

        if (confirm.equalsIgnoreCase("Y")) {
            boolean result = enrollmentService.deleteById(id);

            if (result) {
                System.out.println("Xóa sinh viên khỏi khóa học thành công.");
            } else {
                System.out.println("Xóa thất bại.");
            }
        } else {
            System.out.println("Đã hủy thao tác xóa.");
        }
    }

    private void printEnrollmentTable(List<Enrollment> enrollments) {
        System.out.println("\n============================== DANH SÁCH ĐĂNG KÝ KHÓA HỌC ==============================");

        System.out.printf("%-5s %-12s %-22s %-12s %-25s %-15s%n",
                "ID", "ID học viên", "Tên học viên", "ID khóa học", "Tên khóa học", "Trạng thái");

        System.out.println("-----------------------------------------------------------------------------------------");

        for (Enrollment enrollment : enrollments) {
            System.out.printf("%-5d %-12d %-22s %-12d %-25s %-15s%n",
                    enrollment.getId(),
                    enrollment.getStudentId(),
                    enrollment.getStudentName(),
                    enrollment.getCourseId(),
                    enrollment.getCourseName(),
                    enrollment.getStatus());
        }
    }
}