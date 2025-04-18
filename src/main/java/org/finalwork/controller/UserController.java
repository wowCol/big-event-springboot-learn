package org.finalwork.controller;

import org.finalwork.pojo.User;
import org.finalwork.utils.Md5Util;
import org.finalwork.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.finalwork.pojo.Result;
import org.finalwork.service.UserService;
import org.finalwork.utils.JwtUtil;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/register")
    public Result register(@Pattern(regexp = "\\S{5,16}$") String username,
                           @Pattern(regexp = "\\S{5,16}$") String password) {
        // 校验返回参数
        if (username != null && username.length() >= 5 && username.length() <= 16
                && password != null && password.length() >= 5 && password.length() <= 16) {
            // 查询是否有占用
            User u = userService.findByUserName(username);
            if (u == null) {
                // 没有被占用
                // 注册
                userService.register(username, password);
                return Result.success();
            } else {
                // 被占用
                return Result.error("用户名已被占用");
            }
        } else {
            return Result.error("参数不合法");
        }
    }

    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "\\S{5,16}$") String username,
                                @Pattern(regexp = "\\S{5,16}$") String password) {
        // 根据用户名查询用户
        User loginUser = userService.findByUserName(username);
        // 查询用户是否存在
        if (loginUser == null) {
            return Result.error("用户名不存在");
        }
        // 判断密码是否正确
        if (Md5Util.getMD5String(password).equals(loginUser.getPassword())) {
            // 登录成功
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", loginUser.getId());
            claims.put("username", loginUser.getUsername());
            String token = JwtUtil.genToken(claims);

            // 把token存储到redis中
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(token, token, 1, TimeUnit.HOURS);

            return Result.success(token);
        }
        return Result.error("密码错误");
    }

    @GetMapping("/userInfo")
    public Result<User> userInfo(/*@RequestHeader(name = "Authorization") String token*/) {
//        // 根据用户名查询用户
//        Map<String, Object> stringObjectMap = JwtUtil.parseToken(token);
//        String username = (String) stringObjectMap.get("username");
//        User user = userService.findByUserName(username);
        Map<String, Object> stringObjectMap = ThreadLocalUtil.get();
        String username = (String) stringObjectMap.get("username");
        User user = userService.findByUserName(username);
        return Result.success(user);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user) {
        userService.update(user);
        return Result.success();
    }

    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl) {
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String, String> params, @RequestHeader("Authorization") String token) {
        // 手动调用参数
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");

        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)) {
            return Result.error("缺少必要参数");
        }

        // 检测原密码是否正确
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String)map.get("username");
        User user = userService.findByUserName(username);
        if (!user.getPassword().equals(Md5Util.getMD5String(oldPwd))) {
            return Result.error("原密码填写不正确");
        }

        if (!rePwd.equals(newPwd)) {
            return Result.error("两次填写的新密码不一样");
        }

        // 调用Service完成密码更新
        userService.updatePwd(newPwd);
        // 删除redis中对应的token
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.getOperations().delete(token);
        return Result.success();
    }
}
