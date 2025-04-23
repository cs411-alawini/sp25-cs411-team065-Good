package g65.aggregate;

import g65.vo.UserVO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResultAggregate {
    private String token;
    private UserVO user;
}
