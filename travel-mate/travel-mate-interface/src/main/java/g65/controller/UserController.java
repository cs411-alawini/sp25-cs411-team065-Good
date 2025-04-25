package g65.controller;


import g65.aggregate.LoginResultAggregate;
import g65.api.UserApi;
import g65.dto.LoginRequestDTO;
import g65.dto.LoginResponseDTO;
import g65.dto.RegisterRequestDTO;
import g65.exception.BizException;
import g65.response.Response;
import g65.response.ResponseCode;
import g65.service.UserService;
import g65.util.UserContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Slf4j
@Validated
@CrossOrigin
@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService userService;

    @Override
    public Response<LoginResponseDTO> login(LoginRequestDTO request) {
        log.info("[UserLogin] Login attempt with email: {}", request.getEmail());
        LoginResultAggregate loginResultAggregate = userService.login(
                request.getEmail(),
                request.getPassword(),
                request.isRememberMe());
        return Response.<LoginResponseDTO>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .msg(ResponseCode.SUCCESS.getMessage())
                .data(LoginResponseDTO.builder()
                        .token(loginResultAggregate.getToken())
                        .user(loginResultAggregate.getUser())
                        .build())
                .build();
    }

    @Override
    public Response<Void> logout() {
        Integer userId = UserContext.getUserId();
        String token = UserContext.getToken();

        if (userId == null || token == null) {
            throw new BizException(ResponseCode.UNAUTHORIZED);
        }

        userService.logout();
        log.info("[UserLogout] Logout successful. UserId: {}", userId);

        return Response.<Void>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .msg(ResponseCode.SUCCESS.getMessage())
                .build();
    }

    @Override
    public Response<LoginResponseDTO> register(RegisterRequestDTO request) {
        log.info("[UserRegister] Register attempt with email: {}", request.getEmail());
        LoginResultAggregate result = userService.registerAndLogin(request.getName(), request.getEmail(), request.getPassword());

        return Response.<LoginResponseDTO>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .msg("Registration + login successful")
                .data(LoginResponseDTO.builder()
                        .token(result.getToken())
                        .user(result.getUser())
                        .build())
                .build();
    }
}
