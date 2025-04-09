package g65.repository.mapper;

import g65.repository.po.UserPO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    UserPO findUserByEmail(String email);
}
