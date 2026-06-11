package presentation;

import business.ICourseService;
import business.impl.CourseServiceImpl;
import model.Course;

import java.util.List;
import java.util.Scanner;

public class CourseView {
    private Scanner sc = new Scanner(System.in);
    private ICourseService courseService = new CourseServiceImpl();

    public void showMenu() {
        int choice;

        do {
            System.out.println("\n========== QUẢN LÝ KHÓA HỌC ==========");
            System.out.println("1. Hiển thị danh sách khóa học");
            System.out.println("2. Thêm mới khóa học");
            System.out.println("3. Chỉnh sửa thông tin khóa học");
            System.out.println("4. Xóa khóa học");
            System.out.println("5. Tìm kiếm khóa học");
            System.out.println("6. Sắp xếp khóa học");
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
                    displayCourses();
                    break;

                case 2:
                    addCourse();
                    break;

                case 3:
                    updateCourse();
                    break;

                case 4:
                    deleteCourse();
                    break;

                case 5:
                    searchCourse();
                    break;

                case 6:
                    sortCourse();
                    break;

                case 7:
                    System.out.println("Quay lại menu Admin.");
                    break;

                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }

        } while (choice != 7);
    }

    private void displayCourses() {
        List<Course> courses = courseService.findAll();

        if (courses.isEmpty()) {
            System.out.println("Danh sách khóa học trống.");
            return;
        }

        printCourseTable(courses);
    }

    private void printCourseTable(List<Course> courses) {
        System.out.println("\n============================================ DANH SÁCH KHÓA HỌC ============================================");

        System.out.printf("%-5s %-30s %-15s %-25s %-15s%n",
                "ID", "Tên khóa học", "Thời lượng", "Giảng viên", "Ngày tạo");

        System.out.println("-------------------------------------------------------------------------------------------------------------");

        for (Course course : courses) {
            System.out.printf("%-5d %-30s %-15d %-25s %-15s%n",
                    course.getId(),
                    course.getName(),
                    course.getDuration(),
                    course.getInstructor(),
                    course.getCreatedAt());
        }
    }

    private void addCourse() {
        System.out.println("\n===== THÊM MỚI KHÓA HỌC =====");

        System.out.print("Nhập tên khóa học: ");
        String name = sc.nextLine();

        int duration;
        try {
            System.out.print("Nhập thời lượng khóa học: ");
            duration = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Thời lượng phải là số.");
            return;
        }

        System.out.print("Nhập tên giảng viên: ");
        String instructor = sc.nextLine();

        Course course = new Course();
        course.setName(name);
        course.setDuration(duration);
        course.setInstructor(instructor);

        boolean result = courseService.insert(course);

        if (result) {
            System.out.println("Thêm khóa học thành công.");
        } else {
            System.out.println("Thêm khóa học thất bại. Dữ liệu không hợp lệ.");
        }
    }

    private void updateCourse() {
        System.out.println("\n===== CHỈNH SỬA THÔNG TIN KHÓA HỌC =====");

        int id;
        try {
            System.out.print("Nhập id khóa học cần sửa: ");
            id = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID phải là số.");
            return;
        }

        Course course = courseService.findById(id);

        if (course == null) {
            System.out.println("Không tìm thấy khóa học có id = " + id);
            return;
        }

        int choice;

        do {
            System.out.println("\nThông tin khóa học hiện tại:");
            printCourseTable(List.of(course));

            System.out.println("\nChọn thuộc tính cần sửa:");
            System.out.println("1. Sửa tên khóa học");
            System.out.println("2. Sửa thời lượng");
            System.out.println("3. Sửa giảng viên");
            System.out.println("4. Sửa tất cả thông tin");
            System.out.println("5. Quay lại menu quản lý khóa học");
            System.out.print("Nhập lựa chọn: ");

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Bạn phải nhập số.");
                choice = -1;
            }

            switch (choice) {
                case 1:
                    System.out.print("Nhập tên khóa học mới: ");
                    course.setName(sc.nextLine());

                    if (courseService.update(course)) {
                        System.out.println("Cập nhật tên khóa học thành công.");
                    } else {
                        System.out.println("Cập nhật thất bại. Tên khóa học không được để trống.");
                    }
                    break;

                case 2:
                    try {
                        System.out.print("Nhập thời lượng mới: ");
                        int duration = Integer.parseInt(sc.nextLine());
                        course.setDuration(duration);
                    } catch (NumberFormatException e) {
                        System.out.println("Thời lượng phải là số.");
                        break;
                    }

                    if (courseService.update(course)) {
                        System.out.println("Cập nhật thời lượng thành công.");
                    } else {
                        System.out.println("Cập nhật thất bại. Thời lượng không hợp lệ.");
                    }
                    break;

                case 3:
                    System.out.print("Nhập tên giảng viên mới: ");
                    course.setInstructor(sc.nextLine());

                    if (courseService.update(course)) {
                        System.out.println("Cập nhật giảng viên thành công.");
                    } else {
                        System.out.println("Cập nhật thất bại. Giảng viên không được để trống.");
                    }
                    break;

                case 4:
                    System.out.print("Nhập tên khóa học mới: ");
                    String name = sc.nextLine();

                    int duration;
                    try {
                        System.out.print("Nhập thời lượng mới: ");
                        duration = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Thời lượng phải là số.");
                        break;
                    }

                    System.out.print("Nhập tên giảng viên mới: ");
                    String instructor = sc.nextLine();

                    course.setName(name);
                    course.setDuration(duration);
                    course.setInstructor(instructor);

                    if (courseService.update(course)) {
                        System.out.println("Cập nhật toàn bộ thông tin khóa học thành công.");
                    } else {
                        System.out.println("Cập nhật thất bại. Dữ liệu không hợp lệ.");
                    }
                    break;

                case 5:
                    System.out.println("Quay lại menu quản lý khóa học.");
                    break;

                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }

        } while (choice != 5);
    }

    private void deleteCourse() {
        System.out.println("\n===== XÓA KHÓA HỌC =====");

        int id;
        try {
            System.out.print("Nhập id khóa học cần xóa: ");
            id = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID phải là số.");
            return;
        }

        Course course = courseService.findById(id);

        if (course == null) {
            System.out.println("Không tìm thấy khóa học có id = " + id);
            return;
        }

        System.out.println("\nThông tin khóa học cần xóa:");
        printCourseTable(List.of(course));

        System.out.print("Bạn có chắc chắn muốn xóa khóa học này không? (Y/N): ");
        String confirm = sc.nextLine();

        if (confirm.equalsIgnoreCase("Y")) {
            boolean result = courseService.deleteById(id);

            if (result) {
                System.out.println("Xóa khóa học thành công.");
            } else {
                System.out.println("Xóa khóa học thất bại.");
            }
        } else {
            System.out.println("Đã hủy thao tác xóa.");
        }
    }

    private void searchCourse() {
        System.out.println("\n===== TÌM KIẾM KHÓA HỌC =====");
        System.out.print("Nhập tên khóa học cần tìm: ");
        String keyword = sc.nextLine();

        List<Course> courses = courseService.searchByName(keyword);

        if (courses.isEmpty()) {
            System.out.println("Không tìm thấy khóa học phù hợp.");
            return;
        }

        printCourseTable(courses);
    }

    private void sortCourse() {
        int choice;

        do {
            System.out.println("\n===== SẮP XẾP KHÓA HỌC =====");
            System.out.println("1. Sắp xếp theo ID tăng dần");
            System.out.println("2. Sắp xếp theo ID giảm dần");
            System.out.println("3. Sắp xếp theo tên tăng dần");
            System.out.println("4. Sắp xếp theo tên giảm dần");
            System.out.println("5. Quay lại menu quản lý khóa học");
            System.out.print("Nhập lựa chọn: ");

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Bạn phải nhập số.");
                choice = -1;
            }

            List<Course> courses = null;

            switch (choice) {
                case 1:
                    courses = courseService.sortByIdAsc();
                    break;

                case 2:
                    courses = courseService.sortByIdDesc();
                    break;

                case 3:
                    courses = courseService.sortByNameAsc();
                    break;

                case 4:
                    courses = courseService.sortByNameDesc();
                    break;

                case 5:
                    System.out.println("Quay lại menu quản lý khóa học.");
                    break;

                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }

            if (courses != null) {
                if (courses.isEmpty()) {
                    System.out.println("Danh sách khóa học trống.");
                } else {
                    printCourseTable(courses);
                }
            }

        } while (choice != 5);
    }
}