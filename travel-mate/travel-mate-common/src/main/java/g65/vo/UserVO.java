package g65.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserVO {
    private Integer id;
    private String name;
    private String email;
    private String password;
}
