package dao;

import model.Admin;

public interface IAdminDAO {
    public Admin login(String username,String password);
}
