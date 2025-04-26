package g65.config.interceptor;

import g65.constant.Constants;
import g65.exception.BizException;
import g65.response.ResponseCode;
import g65.util.UserContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Slf4j
@Component
@RequiredArgsConstructor
public class TokenInterceptor implements HandlerInterceptor {

    private final StringRedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new BizException(ResponseCode.TOKEN_INVALID);
        }

        String token = authHeader.substring(7);
        String userId = redisTemplate.opsForValue().get(Constants.RedisKey.LOGIN_TOKEN + token);

        if (userId == null) {
            throw new BizException(ResponseCode.LOGIN_EXPIRED);
        }

        UserContext.setUserId(Integer.parseInt(userId));
        UserContext.setToken(token);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        UserContext.clear();
    }
}
