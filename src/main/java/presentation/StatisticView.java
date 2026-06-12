package presentation;

import business.IStatisticService;
import business.impl.StatisticServiceImpl;
import model.Statistic;

import java.util.List;
import java.util.Scanner;

public class StatisticView {
    private Scanner sc = new Scanner(System.in);
    private IStatisticService statisticService = new StatisticServiceImpl();

    public void showMenu() {
        int choice;

        do {
            System.out.println("\n========== THỐNG KÊ ==========");
            System.out.println("1. Thống kê tổng số khóa học và tổng số học viên");
            System.out.println("2. Thống kê tổng số học viên theo từng khóa");
            System.out.println("3. Thống kê top 5 khóa học đông học viên nhất");
            System.out.println("4. Liệt kê khóa học có trên 10 học viên");
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
                    showTotalCourseAndStudent();
                    break;

                case 2:
                    showStudentsByCourse();
                    break;

                case 3:
                    showTop5Courses();
                    break;

                case 4:
                    showCoursesMoreThan10Students();
                    break;

                case 5:
                    System.out.println("Quay lại menu Admin.");
                    break;

                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }

        } while (choice != 5);
    }

    private void showTotalCourseAndStudent() {
        int totalCourses = statisticService.countCourses();
        int totalStudents = statisticService.countStudents();

        System.out.println("\n===== THỐNG KÊ TỔNG QUAN =====");
        System.out.println("Tổng số khóa học: " + totalCourses);
        System.out.println("Tổng số học viên: " + totalStudents);
    }

    private void showStudentsByCourse() {
        List<Statistic> statistics = statisticService.countStudentsByCourse();

        if (statistics.isEmpty()) {
            System.out.println("Không có dữ liệu thống kê.");
            return;
        }

        System.out.println("\n===== SỐ HỌC VIÊN THEO TỪNG KHÓA =====");
        printStatisticTable(statistics);
    }

    private void showTop5Courses() {
        List<Statistic> statistics = statisticService.top5CoursesByStudents();

        if (statistics.isEmpty()) {
            System.out.println("Không có dữ liệu thống kê.");
            return;
        }

        System.out.println("\n===== TOP 5 KHÓA HỌC ĐÔNG HỌC VIÊN NHẤT =====");
        printStatisticTable(statistics);
    }

    private void showCoursesMoreThan10Students() {
        List<Statistic> statistics = statisticService.coursesMoreThan10Students();

        if (statistics.isEmpty()) {
            System.out.println("Không có khóa học nào có trên 10 học viên.");
            return;
        }

        System.out.println("\n===== KHÓA HỌC CÓ TRÊN 10 HỌC VIÊN =====");
        printStatisticTable(statistics);
    }

    private void printStatisticTable(List<Statistic> statistics) {
        System.out.printf("%-12s %-35s %-15s%n",
                "ID khóa", "Tên khóa học", "Số học viên");

        System.out.println("----------------------------------------------------------------");

        for (Statistic statistic : statistics) {
            System.out.printf("%-12d %-35s %-15d%n",
                    statistic.getCourseId(),
                    statistic.getCourseName(),
                    statistic.getTotalStudents());
        }
    }
}