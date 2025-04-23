package g65.repository.po;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserPO {
    private Integer id;
    private String name;
    private String email;
    private String password;
}
