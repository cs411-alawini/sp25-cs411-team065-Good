package g65.repository;

import g65.repository.mapper.UserMapper;
import g65.repository.po.UserPO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final UserMapper userMapper;

    public UserPO findUserByEmail(String email) {
        return userMapper.findUserByEmail(email);
    }
}
