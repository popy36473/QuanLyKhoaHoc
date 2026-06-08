package presentation;

import business.ICourseService;
import business.impl.CourseServiceImpl;

import java.util.Scanner;

public class AdminView {



    public void showMenu(){
        int choose;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("========= MENU ADMIN =========");
            System.out.println(" 1. Quản lý khóa học ");
            System.out.println(" 2. Quản lý học viên ");
            System.out.println(" 3. Quản lý đăng ký học ");
            System.out.println(" 4.Thống kê học viên theo khóa học ");
            System.out.println(" 5.Đăng xuất ");
            System.out.println("===============================");
            System.out.println(" Nhập lựa chọn của bạn: ");
            try {
                choose = Integer.parseInt(sc.nextLine());
            }catch (NumberFormatException e){
                System.out.println(" Lựa chọn không hợp lệ ");
                choose = -1;
            }
            switch (choose) {
                case 1:
                    CourseView courseView = new CourseView();
                    courseView.showMenu();
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Vui lòng nhập số từ 1 - 5 ");
                    break;
            }
        }while (choose != 5);
    }





}
