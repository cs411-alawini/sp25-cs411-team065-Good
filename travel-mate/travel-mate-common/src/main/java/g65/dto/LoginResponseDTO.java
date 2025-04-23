package g65.dto;

import g65.vo.UserVO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDTO {
    private String token;
    private UserVO user;
}
