package g65.service.impl;


import g65.aggregate.LoginResultAggregate;
import g65.constant.Constants;
import g65.exception.BizException;
import g65.repository.UserRepository;
import g65.repository.po.UserPO;
import g65.response.ResponseCode;
import g65.service.UserService;
import g65.util.UserContext;
import g65.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public LoginResultAggregate login(String email, String password) {
        UserPO userPO = userRepository.findUserByEmail(email);
        if (userPO == null) {
            log.warn("[UserLogin] Login failed. Email not found: {}", email);
            throw new BizException(ResponseCode.USER_NOT_FOUND);
        }
        if (!userPO.getPassword().equals(password)) {
            log.warn("[UserLogin] Login failed. Incorrect password for userId: {}", userPO.getId());
            throw new BizException(ResponseCode.PASSWORD_INCORRECT);
        }
        String token = UUID.randomUUID().toString();
        UserVO userVO = UserVO.builder()
                .id(userPO.getId())
                .name(userPO.getName())
                .email(userPO.getEmail())
                .build();
        log.info("[UserLogin] Login success. UserId: {}, Token: {}", userPO.getId(), token);
        stringRedisTemplate.opsForValue().set(Constants.RedisKey.LOGIN_TOKEN + token, userPO.getId().toString(), 30, TimeUnit.MINUTES);
        return LoginResultAggregate.builder()
                .token(token)
                .user(userVO)
                .build();
    }

    @Override
    public void logout() {

        Integer userId = UserContext.getUserId();
        String token = UserContext.getToken();

        String key = Constants.RedisKey.LOGIN_TOKEN + token;
        stringRedisTemplate.delete(key);
        log.info("[UserLogout] Logout success. UserId: {}, Token: {}", userId, token);
    }

    @Override
    public LoginResultAggregate registerAndLogin(String name, String email, String password) {
        UserPO exists = userRepository.findUserByEmail(email);
        if (exists != null) {
            log.warn("[UserRegister] Email already exists: {}", email);
            throw new BizException(ResponseCode.USER_ALREADY_EXISTS);
        }
        UserPO userPO = UserPO.builder()
                .name(name)
                .email(email)
                .password(password)
                .build();
        userRepository.saveUser(userPO);
        log.info("[UserRegister] Register success. Email: {}, Name: {}", email, name);
        return login(email, password);
    }
}
