package g65.service;

import g65.aggregate.LoginResultAggregate;

public interface UserService {

    LoginResultAggregate login(String email, String password);

    void logout();

    LoginResultAggregate registerAndLogin(String name, String email, String password);
}
