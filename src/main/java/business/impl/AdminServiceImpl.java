package business.impl;

import business.IAdminService;
import dao.IAdminDAO;
import dao.impl.AdminDAOImpl;
import model.Admin;

public class AdminServiceImpl implements IAdminService {

    private final IAdminDAO adminDAO;
    public AdminServiceImpl(){
        this.adminDAO = new AdminDAOImpl();
    }

    @Override
    public Admin login(String username, String password) {
        if (username == null || username.trim().isEmpty()){
            return null;
        }
        if (password == null || username.trim().isEmpty()){
            return null;
        }
        return adminDAO.login(username.trim(),password.trim());
    }
}
