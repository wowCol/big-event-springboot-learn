package org.finalwork.interceptors;

import org.finalwork.utils.JwtUtil;
import org.finalwork.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 令牌验证
        String token = request.getHeader("Authorization");
        // 提供服务前先验证token
        try {
            // 从redis中获取相同的token
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            String redisToken = operations.get(token);
            if (redisToken == null) {
                // token已经失效
                throw new RuntimeException();
            }

            Map<String, Object> claims = JwtUtil.parseToken(token);

            // 把业务数据存取在ThreadLocal中
            ThreadLocalUtil.set(claims);
            return true; // 放行
        } catch (Exception e) {
            // http响应状态码为401
            response.setStatus(401);
            return false; // 不放行
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 清空ThreadLocal中的数据，防止内存泄漏
        ThreadLocalUtil.remove();
    }
}
