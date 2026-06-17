package presentation;

import java.util.Scanner;

public class AdminView {
    private Scanner sc = new Scanner(System.in);

    public void showMenu() {
        int choice;

        do {
            System.out.println("\n========== MENU ADMIN ==========");
            System.out.println("1. Quản lý khóa học");
            System.out.println("2. Quản lý học viên");
            System.out.println("3. Quản lý đăng ký học");
            System.out.println("4. Thống kê");
            System.out.println("5. Đăng xuất");
            System.out.print("Nhập lựa chọn: ");

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Bạn phải nhập số.");
                choice = -1;
            }

            switch (choice) {
                case 1:
                    CourseView courseView = new CourseView();
                    courseView.showMenu();
                    break;

                case 2:
                    StudentView studentView = new StudentView();
                    studentView.showMenu();
                    break;

                case 3:
                    EnrollmentView enrollmentView = new EnrollmentView();
                    enrollmentView.showMenu();
                    break;

                case 4:
                    StatisticView statisticView = new StatisticView();
                    statisticView.showMenu();
                    break;

                case 5:
                    System.out.println("Đăng xuất thành công.");
                    break;

                default:
                    System.out.println("Lựa chọn không hợp lệ.");
                    break;
            }

        } while (choice != 5);
    }
}