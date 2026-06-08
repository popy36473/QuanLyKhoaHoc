package presentation;

import business.ICourseService;
import business.impl.CourseServiceImpl;
import model.Course;

import java.util.List;
import java.util.Scanner;

public class CourseView {
    private Scanner sc = new Scanner(System.in);
    private ICourseService courseService = new CourseServiceImpl();

    public void showMenu(){

        int choice;

        do {
            System.out.println("1. Hiển thị danh sách khóa học");
            System.out.println("2. Thêm mới khóa học");
            System.out.println("3. Chỉnh sửa thông tin khóa học");
            System.out.println("4. Xóa khóa học");
            System.out.println("5. Tìm kiếm theo tên");
            System.out.println("6. Sắp xếp khóa học");
            System.out.println("7. Quay lại menu Admin ");
            System.out.println("Nhập lựa chọn: ");

            try {
                choice = Integer.parseInt(sc.nextLine());
            }catch (NumberFormatException e){
                System.out.println("Bạn phải nhập số từ 1 - 7 ");
                choice =-1;
            }

            switch (choice){
                case 1:
                    displayCourses(courseService.findAll());
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
                    System.out.println("Quay lại menu admin ");
                    break;
            }
        }while (choice != 7);
    }


    public void displayCourses(List<Course> courses){
        if(courses == null || courses.isEmpty()){
            System.out.println("Không có khóa học nào.");
            return;
        }
        System.out.println("Danh sách khóa học ");
        System.out.println("====================================");
        for (Course course : courses){
            System.out.println(course);
        }
    }

    private void addCourse(){
        System.out.println("======= Thêm mới khóa học =======");
        System.out.println("Nhập tên khóa học");
        String name = sc.nextLine();

        System.out.println("Nhập thời lượng:");
        int duration;
        try {
            duration = Integer.parseInt(sc.nextLine());
        }catch (NumberFormatException e){
            System.out.println("Thời lượng phải là số");
            return;
        }

        System.out.println("Nhập giảng viên");
        String instructor = sc.nextLine();

        Course course = new Course(name,duration,instructor);

        boolean result = courseService.insert(course);
        if (result){
            System.out.println("Thêm khóa học thành công");
        }else {
            System.out.println("Thêm khóa học thất bại. Dữ liệu không hợp lệ");
        }

    }

    private void updateCourse(){
        System.out.println("======== Chỉnh sửa thông tin khóa học ========");
        int id;

        System.out.println("Nhập id khóa học cần sửa:");
        try {
            id = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID phải là số.");
            return;
        }
        Course course = courseService.findById(id);
        if(course == null){
            System.out.println("Không tìm thấy khóa học có id = "+id);
            return;
        }

        int choice;

        do {
            System.out.println("\nThông tin khóa học hiện tại:");
            System.out.println(course);

            System.out.println("\nChọn thuộc tính cần sửa:");
            System.out.println("1. Sửa tên khóa học");
            System.out.println("2. Sửa thời lượng");
            System.out.println("3. Sửa giảng viên");
            System.out.println("4. Sửa tất cả thông tin");
            System.out.println("5. Quay lại");
            System.out.print("Nhập lựa chọn: ");

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Bạn phải nhập số từ 1 - 5.");
                choice = -1;
            }

            switch (choice){
                case 1:
                    System.out.println("Nhập tên khóa học mới ");
                    String name = sc.nextLine();
                    course.setName(name);
                    if (courseService.update(course)) {
                        System.out.println("Cập nhật tên khóa học thành công.");
                    } else {
                        System.out.println("Cập nhật thất bại. Tên khóa học không được để trống.");
                    }
                    break;
                case 2:
                    System.out.print("Nhập thời lượng mới: ");
                    int duration;

                    try {
                        duration = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Thời lượng phải là số.");
                        break;
                    }

                    course.setDuration(duration);

                    if (courseService.update(course)) {
                        System.out.println("Cập nhật thời lượng thành công.");
                    } else {
                        System.out.println("Cập nhật thất bại. Thời lượng phải lớn hơn 0.");
                    }
                    break;
                case 3:
                    System.out.print("Nhập giảng viên mới: ");
                    String instructor = sc.nextLine();
                    course.setInstructor(instructor);

                    if (courseService.update(course)) {
                        System.out.println("Cập nhật giảng viên thành công.");
                    } else {
                        System.out.println("Cập nhật thất bại. Giảng viên không được để trống.");
                    }
                    break;
                case 4:
                    System.out.print("Nhập tên khóa học mới: ");
                    String newName = sc.nextLine();

                    System.out.print("Nhập thời lượng mới: ");
                    int newDuration;

                    try {
                        newDuration = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Thời lượng phải là số.");
                        break;
                    }

                    System.out.print("Nhập giảng viên mới: ");
                    String newInstructor = sc.nextLine();

                    course.setName(newName);
                    course.setDuration(newDuration);
                    course.setInstructor(newInstructor);

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
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn từ 1 - 5.");
                    break;
            }
        }while (choice != 5);
    }

    private void deleteCourse() {
        System.out.println("===== XÓA KHÓA HỌC =====");

        System.out.print("Nhập id khóa học cần xóa: ");
        int id;

        try {
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

        System.out.println("Thông tin khóa học cần xóa:");
        System.out.println(course);

        System.out.print("Bạn có chắc chắn muốn xóa không? (Y/N): ");
        String confirm = sc.nextLine();

        if (!confirm.equalsIgnoreCase("Y")) {
            System.out.println("Đã hủy thao tác xóa.");
            return;
        }

        boolean result = courseService.deleteById(id);

        if (result) {
            System.out.println("Xóa khóa học thành công.");
        } else {
            System.out.println("Xóa khóa học thất bại.");
        }
    }
    private void searchCourse() {
        System.out.println("===== TÌM KIẾM KHÓA HỌC THEO TÊN =====");

        System.out.print("Nhập tên khóa học cần tìm: ");
        String keyword = sc.nextLine();

        List<Course> courses = courseService.searchByName(keyword);

        if (courses == null || courses.isEmpty()) {
            System.out.println("Không tìm thấy khóa học phù hợp.");
            return;
        }

        displayCourses(courses);
    }

    private void sortCourse() {
        int choice;

        do {
            System.out.println("===== SẮP XẾP KHÓA HỌC =====");
            System.out.println("1. Sắp xếp theo ID tăng dần");
            System.out.println("2. Sắp xếp theo ID giảm dần");
            System.out.println("3. Sắp xếp theo tên tăng dần");
            System.out.println("4. Sắp xếp theo tên giảm dần");
            System.out.println("5. Quay lại menu quản lý khóa học");
            System.out.print("Nhập lựa chọn: ");

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Bạn phải nhập số từ 1 - 5.");
                choice = -1;
            }

            switch (choice) {
                case 1:
                    displayCourses(courseService.sortByIdAsc());
                    break;

                case 2:
                    displayCourses(courseService.sortByIdDesc());
                    break;

                case 3:
                    displayCourses(courseService.sortByNameAsc());
                    break;

                case 4:
                    displayCourses(courseService.sortByNameDesc());
                    break;

                case 5:
                    System.out.println("Quay lại menu quản lý khóa học.");
                    break;

                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn từ 1 - 5.");
                    break;
            }

        } while (choice != 5);
    }
}
