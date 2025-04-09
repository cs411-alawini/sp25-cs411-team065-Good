package g65.api;


import g65.dto.LoginRequestDTO;
import g65.dto.LoginResponseDTO;
import g65.response.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserApi {

    /**
     * Handles user login by validating credentials and issuing an authentication token.
     *
     * @param request the login request DTO containing user's email and password
     * @return a standardized response containing a token and basic user info
     */
    @PostMapping("/login")
    Response<LoginResponseDTO> login(@RequestBody LoginRequestDTO request);
}
