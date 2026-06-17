package presentation;

import business.IAdminService;
import business.impl.AdminServiceImpl;
import model.Admin;

import java.util.Scanner;

public class MainView {
    private final Scanner sc = new Scanner(System.in);
    private final IAdminService adminService = new AdminServiceImpl();


    public void showMenu(){
        int choice;
        do {
            System.out.println("========== HỆ THỐNG QUẢN LÝ ĐÀO TẠO ========== ");
            System.out.println(" 1: Đăng nhập với tư cách quản trị viên ");
            System.out.println(" 2: Đăng nhập với tư cách học viên ");
            System.out.println(" 3: Thoát ");
            System.out.println(" Nhập lựa chọn của bạn:  ");
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e){
                System.out.println(" Lựa chọn không hợp lệ ");
                choice = -1;
            }

            switch (choice){
                case 1:
                    loginAdmin();
                    break;
                case 2:
                    StudentAccountView studentAccountView = new StudentAccountView();
                    studentAccountView.showMenu();
                    break;
                case 3:
                    System.out.println("Thoát chương trình");
                    break;
                default:
                    System.out.println("Vui lòng chọn từ 1 - 3 :");
                    break;
            }
        }while(choice!=3);
    }




    private void loginAdmin(){
        while(true){
            System.out.println("========= ĐĂNG NHẬP ADMIN ========= ");

            System.out.println("Nhập username: ");
            String username = sc.nextLine();

            System.out.println("Nhập password: ");
            String password = sc.nextLine();

            Admin admin = adminService.login(username,password);

            if (admin != null){
                System.out.println(" Đăng nhập thành công! ");
                System.out.println(" Xin chào admin "+ admin.getUsername());

                AdminView adminView = new AdminView();
                adminView.showMenu();
                return;
            }

            System.out.println(" Tài khoản hoặc mật khẩu không đúng! ");
            int choice;
            do {
                System.out.println(" Nhập 1 để nhập lại \n Nhập 2 để về menu chính ");
                System.out.print(" Nhập lựa chọn : ");

                try {
                    choice = Integer.parseInt(sc.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println(" Lựa chọn không hợp lệ !");
                    choice = -1;
                }

                switch (choice) {
                    case 1:
                        break;

                    case 2:
                        System.out.println(" Đã quay lại Menu chính ");
                        return;

                    default:
                        System.out.println(" Vui lòng chọn 1 hoặc 2 ");
                        break;
                }
            } while (choice != 1);
        }
    }
}
