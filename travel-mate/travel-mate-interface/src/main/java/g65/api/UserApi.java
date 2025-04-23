package g65.api;


import g65.dto.LoginRequestDTO;
import g65.dto.LoginResponseDTO;
import g65.dto.RegisterRequestDTO;
import g65.response.Response;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/user")
public interface UserApi {

    /**
     * Handles user login by validating credentials and issuing an authentication token.
     *
     * @param request the login request DTO containing user's email and password
     * @return a standardized response containing a token and basic user info
     */
    @PostMapping("/login")
    Response<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO request);

    /**
     * Handles user logout by invalidating the authentication token stored in Redis.
     *
     * @return a standardized response indicating whether logout was successful
     */
    @PostMapping("/logout")
    Response<Void> logout();

    /**
     * Handles user registration by validating and saving new user credentials.
     * Upon successful registration, the user is automatically logged in and issued an authentication token.
     *
     * @param request the registration request DTO containing the user's name, email, and password
     * @return a standardized response containing a token and basic user info
     */
    @PostMapping("/register")
    Response<LoginResponseDTO> register(@RequestBody @Valid RegisterRequestDTO request);
}
