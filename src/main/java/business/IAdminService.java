package business;

import model.Admin;

public interface IAdminService {
    public Admin login(String username, String password);
}
