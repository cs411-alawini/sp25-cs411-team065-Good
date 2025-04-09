package g65.controller;


import g65.api.UserApi;
import g65.dto.LoginRequestDTO;
import g65.dto.LoginResponseDTO;
import g65.response.Response;
import g65.response.ResponseCode;
import g65.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Slf4j
public class UserController implements UserApi {

    private final UserService userService;

    @Override
    public Response<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        log.info("[UserLogin] Login attempt with email: {}", request.getEmail());
        String token = userService.login(request.getEmail(), request.getPassword());
        LoginResponseDTO loginResponseDTO = LoginResponseDTO.builder()
                .token(token)
                .build();
        return Response.<LoginResponseDTO>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .msg(ResponseCode.SUCCESS.getMessage())
                .data(loginResponseDTO)
                .build();
    }
}
