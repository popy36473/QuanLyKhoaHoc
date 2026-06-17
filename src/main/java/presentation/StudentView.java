package presentation;

import business.IStudentService;
import business.impl.StudentServiceImpl;
import model.Student;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class StudentView {
    private Scanner sc = new Scanner(System.in);
    private IStudentService studentService = new StudentServiceImpl();

    public void showMenu() {
        int choice;

        do {
            System.out.println("\n========== QUẢN LÝ HỌC VIÊN ==========");
            System.out.println("1. Hiển thị danh sách học viên");
            System.out.println("2. Thêm mới học viên");
            System.out.println("3. Chỉnh sửa thông tin học viên");
            System.out.println("4. Xóa học viên");
            System.out.println("5. Tìm kiếm học viên");
            System.out.println("6. Sắp xếp học viên");
            System.out.println("7. Quay lại menu Admin");
            System.out.print("Nhập lựa chọn: ");

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Bạn phải nhập số.");
                choice = -1;
            }

            switch (choice) {
                case 1:
                    displayStudentPaging();
                    break;

                case 2:
                    addStudent();
                    break;

                case 3:
                    updateStudent();
                    break;

                case 4:
                    deleteStudent();
                    break;

                case 5:
                    searchStudent();
                    break;

                case 6:
                    sortStudent();
                    break;

                case 7:
                    System.out.println("Quay lại menu Admin.");
                    break;

                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }

        } while (choice != 7);
    }

    private void displayStudents() {
        List<Student> students = studentService.findAll();

        if (students.isEmpty()) {
            System.out.println("Danh sách học viên trống.");
            return;
        }

        printStudentTable(students);
    }

    private void printStudentTable(List<Student> students) {
        System.out.println("\n====================================================== DANH SÁCH HỌC VIÊN ======================================================");

        System.out.printf("%-5s %-25s %-15s %-30s %-12s %-15s %-15s%n",
                "ID", "Tên học viên", "Ngày sinh", "Email", "Giới tính", "SĐT", "Ngày tạo");

        System.out.println("-------------------------------------------------------------------------------------------------------------------------------");

        for (Student student : students) {
            String gender = "1".equals(student.getSex()) ? "Nam" : "Nữ";

            System.out.printf("%-5d %-25s %-15s %-30s %-12s %-15s %-15s%n",
                    student.getId(),
                    student.getName(),
                    student.getDob(),
                    student.getEmail(),
                    gender,
                    student.getPhone(),
                    student.getCreatedAt());
        }
    }

    private void addStudent() {
        System.out.println("\n===== THÊM MỚI HỌC VIÊN =====");

        System.out.print("Nhập tên học viên: ");
        String name = sc.nextLine();

        System.out.print("Nhập ngày sinh, dạng yyyy-MM-dd: ");
        String dobInput = sc.nextLine();

        LocalDate dob;
        try {
            dob = LocalDate.parse(dobInput);
        } catch (Exception e) {
            System.out.println("Ngày sinh không hợp lệ. Ví dụ đúng: 2004-09-12");
            return;
        }

        System.out.print("Nhập email: ");
        String email = sc.nextLine();

        System.out.print("Nhập giới tính (1: Nam, 0: Nữ): ");
        String sex = sc.nextLine();

        System.out.print("Nhập số điện thoại: ");
        String phone = sc.nextLine();

        System.out.print("Nhập mật khẩu: ");
        String password = sc.nextLine();

        Student student = new Student();
        student.setName(name);
        student.setDob(dob);
        student.setEmail(email);
        student.setSex(sex);
        student.setPhone(phone);
        student.setPassword(password);

        boolean result = studentService.insert(student);

        if (result) {
            System.out.println("Thêm học viên thành công.");
        } else {
            System.out.println("Thêm học viên thất bại. Dữ liệu không hợp lệ.");
        }
    }

    private void updateStudent() {
        System.out.println("\n===== CHỈNH SỬA THÔNG TIN HỌC VIÊN =====");

        int id;
        try {
            System.out.print("Nhập id học viên cần sửa: ");
            id = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID phải là số.");
            return;
        }

        Student student = studentService.findById(id);

        if (student == null) {
            System.out.println("Không tìm thấy học viên có id = " + id);
            return;
        }

        int choice;

        do {
            System.out.println("\nThông tin học viên hiện tại:");
            printStudentTable(List.of(student));

            System.out.println("\nChọn thuộc tính cần sửa:");
            System.out.println("1. Sửa tên học viên");
            System.out.println("2. Sửa ngày sinh");
            System.out.println("3. Sửa email");
            System.out.println("4. Sửa giới tính");
            System.out.println("5. Sửa số điện thoại");
            System.out.println("6. Sửa mật khẩu");
            System.out.println("7. Sửa tất cả thông tin");
            System.out.println("8. Quay lại menu quản lý học viên");
            System.out.print("Nhập lựa chọn: ");

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Bạn phải nhập số.");
                choice = -1;
            }

            switch (choice) {
                case 1:
                    System.out.print("Nhập tên học viên mới: ");
                    student.setName(sc.nextLine());

                    if (studentService.update(student)) {
                        System.out.println("Cập nhật tên học viên thành công.");
                    } else {
                        System.out.println("Cập nhật thất bại. Tên học viên không được để trống.");
                    }
                    break;

                case 2:
                    System.out.print("Nhập ngày sinh mới, dạng yyyy-MM-dd: ");
                    String dobInput = sc.nextLine();

                    try {
                        student.setDob(LocalDate.parse(dobInput));
                    } catch (Exception e) {
                        System.out.println("Ngày sinh không hợp lệ. Ví dụ đúng: 2004-09-12");
                        break;
                    }

                    if (studentService.update(student)) {
                        System.out.println("Cập nhật ngày sinh thành công.");
                    } else {
                        System.out.println("Cập nhật thất bại.");
                    }
                    break;

                case 3:
                    System.out.print("Nhập email mới: ");
                    student.setEmail(sc.nextLine());

                    if (studentService.update(student)) {
                        System.out.println("Cập nhật email thành công.");
                    } else {
                        System.out.println("Cập nhật thất bại. Email không hợp lệ.");
                    }
                    break;

                case 4:
                    System.out.print("Nhập giới tính mới (1: Nam, 0: Nữ): ");
                    student.setSex(sc.nextLine());

                    if (studentService.update(student)) {
                        System.out.println("Cập nhật giới tính thành công.");
                    } else {
                        System.out.println("Cập nhật thất bại. Giới tính chỉ được nhập 1 hoặc 0.");
                    }
                    break;

                case 5:
                    System.out.print("Nhập số điện thoại mới: ");
                    student.setPhone(sc.nextLine());

                    if (studentService.update(student)) {
                        System.out.println("Cập nhật số điện thoại thành công.");
                    } else {
                        System.out.println("Cập nhật thất bại. Số điện thoại không được để trống.");
                    }
                    break;

                case 6:
                    System.out.print("Nhập mật khẩu mới: ");
                    student.setPassword(sc.nextLine());

                    if (studentService.update(student)) {
                        System.out.println("Cập nhật mật khẩu thành công.");
                    } else {
                        System.out.println("Cập nhật thất bại. Mật khẩu không được để trống.");
                    }
                    break;

                case 7:
                    System.out.print("Nhập tên học viên mới: ");
                    String name = sc.nextLine();

                    System.out.print("Nhập ngày sinh mới, dạng yyyy-MM-dd: ");
                    String newDobInput = sc.nextLine();

                    LocalDate dob;
                    try {
                        dob = LocalDate.parse(newDobInput);
                    } catch (Exception e) {
                        System.out.println("Ngày sinh không hợp lệ. Ví dụ đúng: 2004-09-12");
                        break;
                    }

                    System.out.print("Nhập email mới: ");
                    String email = sc.nextLine();

                    System.out.print("Nhập giới tính mới (1: Nam, 0: Nữ): ");
                    String sex = sc.nextLine();

                    System.out.print("Nhập số điện thoại mới: ");
                    String phone = sc.nextLine();

                    System.out.print("Nhập mật khẩu mới: ");
                    String password = sc.nextLine();

                    student.setName(name);
                    student.setDob(dob);
                    student.setEmail(email);
                    student.setSex(sex);
                    student.setPhone(phone);
                    student.setPassword(password);

                    if (studentService.update(student)) {
                        System.out.println("Cập nhật toàn bộ thông tin học viên thành công.");
                    } else {
                        System.out.println("Cập nhật thất bại. Dữ liệu không hợp lệ.");
                    }
                    break;

                case 8:
                    System.out.println("Quay lại menu quản lý học viên.");
                    break;

                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }

        } while (choice != 8);
    }

    private void deleteStudent() {
        System.out.println("\n===== XÓA HỌC VIÊN =====");

        int id;
        try {
            System.out.print("Nhập id học viên cần xóa: ");
            id = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID phải là số.");
            return;
        }

        Student student = studentService.findById(id);

        if (student == null) {
            System.out.println("Không tìm thấy học viên có id = " + id);
            return;
        }

        System.out.println("\nThông tin học viên cần xóa:");
        printStudentTable(List.of(student));

        System.out.print("Bạn có chắc chắn muốn xóa học viên này không? (Y/N): ");
        String confirm = sc.nextLine();

        if (confirm.equalsIgnoreCase("Y")) {
            boolean result = studentService.deleteById(id);

            if (result) {
                System.out.println("Xóa học viên thành công.");
            } else {
                System.out.println("Xóa học viên thất bại.");
            }
        } else {
            System.out.println("Đã hủy thao tác xóa.");
        }
    }

    private void searchStudent() {
        System.out.println("\n===== TÌM KIẾM HỌC VIÊN =====");
        System.out.print("Nhập tên, email hoặc id cần tìm: ");
        String keyword = sc.nextLine();

        List<Student> students = studentService.search(keyword);

        if (students.isEmpty()) {
            System.out.println("Không tìm thấy học viên phù hợp.");
            return;
        }

        printStudentTable(students);
    }

    private void sortStudent() {
        int choice;

        do {
            System.out.println("\n===== SẮP XẾP HỌC VIÊN =====");
            System.out.println("1. Sắp xếp theo ID tăng dần");
            System.out.println("2. Sắp xếp theo ID giảm dần");
            System.out.println("3. Sắp xếp theo tên tăng dần");
            System.out.println("4. Sắp xếp theo tên giảm dần");
            System.out.println("5. Quay lại menu quản lý học viên");
            System.out.print("Nhập lựa chọn: ");

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Bạn phải nhập số.");
                choice = -1;
            }

            List<Student> students = null;

            switch (choice) {
                case 1:
                    students = studentService.sortByIdAsc();
                    break;

                case 2:
                    students = studentService.sortByIdDesc();
                    break;

                case 3:
                    students = studentService.sortByNameAsc();
                    break;

                case 4:
                    students = studentService.sortByNameDesc();
                    break;

                case 5:
                    System.out.println("Quay lại menu quản lý học viên.");
                    break;

                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }

            if (students != null) {
                if (students.isEmpty()) {
                    System.out.println("Danh sách học viên trống.");
                } else {
                    printStudentTable(students);
                }
            }

        } while (choice != 5);
    }
    private void displayStudentPaging(){
        List<Student> students = studentService.findAll();

        if (students == null || students.isEmpty()) {
            System.out.println("Chưa có học viên nào.");
            return;
        }

        int pageSize = 5;
        int currentPage = 1;
        int totalStudent = students.size();
        int totalPage =(int) Math.ceil((double) totalStudent / pageSize);

        while (true){
            int fromIndex = (currentPage - 1) * pageSize;
            int toIndex =   Math.min((fromIndex + pageSize) , totalStudent);

            List<Student> studentsOnPage = students.subList(fromIndex,toIndex);
            printStudentTable(studentsOnPage);

            System.out.println("\nTrang " + currentPage + "/" + totalPage);
            System.out.println("1. Trang sau");
            System.out.println("2. Trang trước");
            System.out.println("3. Quay lại");
            System.out.print("Chọn: ");

            int choice = inputInt();
            switch (choice){
                case 1:
                    if (currentPage < totalPage) {
                        currentPage++;
                    } else {
                        System.out.println("Đang ở trang cuối.");
                    }
                    break;

                case 2:
                    if (currentPage > 1) {
                        currentPage--;
                    } else {
                        System.out.println("Đang ở trang đầu.");
                    }
                    break;

                case 3:
                    return;

                default:
                    System.out.println("Lựa chọn không hợp lệ.");
                    break;
            }

        }
    }

    private int inputInt() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Vui lòng nhập số: ");
            }
        }
    }
}