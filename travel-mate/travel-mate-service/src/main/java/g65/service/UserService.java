package g65.service;

import g65.aggregate.LoginResultAggregate;
import g65.exception.BizException;

public interface UserService {

    /**
     * Authenticates a user with email and password, issues a new authentication token,
     * and returns user details along with the token.
     *
     * @param email    the user's email address
     * @param password the user's plaintext password
     * @return a LoginResultAggregate containing the generated token and user information
     * @throws BizException if the email is not found or the password is incorrect
     */
    LoginResultAggregate login(String email, String password);

    /**
     * Logs out the current user by invalidating their authentication token in Redis.
     *
     * @throws BizException if no valid user context or token is found
     */
    void logout();

    /**
     * Registers a new user with the given name, email, and password, then automatically
     * logs them in and returns their authentication details.
     *
     * @param name     the new user's display name
     * @param email    the new user's email address
     * @param password the new user's plaintext password
     * @return a LoginResultAggregate containing the generated token and the newly created user info
     * @throws BizException if the email is already registered
     */
    LoginResultAggregate registerAndLogin(String name, String email, String password);
}
