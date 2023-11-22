package com.checo.bigevent.controller;

import com.checo.bigevent.common.Result;
import com.checo.bigevent.entity.User;
import com.checo.bigevent.service.UserService;
import com.checo.bigevent.util.JwtUtil;
import com.checo.bigevent.util.Md5Util;
import com.checo.bigevent.util.ThreadLocalUtil;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("register")
    public Result<String> register(@Size(min = 5, max = 16, message = "账号必须是 5-16 位") String username,
                                   @Size(min = 5, max = 16, message = "密码必须是 5-16 位") String password) {
        User user = userService.findByUserName(username);
        if (user == null) {
            userService.register(username, password);
            return Result.success("注册成功");
        } else {
            return Result.error("用户名已注册");
        }
    }

    @PostMapping("login")
    public Result<String> login(@Size(min = 5, max = 16, message = "账号必须是 5-16 位") String username,
                                @Size(min = 5, max = 16, message = "密码必须是 5-16 位") String password) {
        User user = userService.findByUserName(username);
        if (user == null) {
            return Result.error("账号不存在");
        } else if (!Md5Util.checkPassword(password, user.getPassword())) {
            return Result.error("密码错误");
        } else {
            HashMap<String, Object> claims = new HashMap<String, Object>() {{
                put("id", user.getId());
                put("username", user.getUsername());
            }};
            String jwtToken = JwtUtil.genToken(claims);

            // 将 token 存储到 Redis
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(jwtToken, jwtToken, 1, TimeUnit.HOURS);

            return Result.success(jwtToken);
        }
    }

    @GetMapping("userInfo")
    public Result<User> userInfo() {
        Map<String, Object> claims = ThreadLocalUtil.get();
        String username = (String) claims.get("username");
        User user = userService.findByUserName(username);
        return Result.success(user);
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody @Validated User user) {
        user.setUpdateTime(LocalDateTime.now());
        userService.updateById(user);
        return Result.success("更新用户信息成功");
    }

    @PatchMapping("updateAvatar")
    public Result<String> updateAvatar(@RequestParam @URL String avatarUrl) {
        userService.updateAvatar(avatarUrl);
        return Result.success("更新头像成功");
    }

    @PatchMapping("updatePwd")
    public Result<String> updatePwd(@RequestBody Map<String, String> updatePwdParams, @RequestHeader("Authorization") String jwtToken) {
        String oldPassword = updatePwdParams.get("old_pwd");
        String newPassword = updatePwdParams.get("new_pwd");
        String repeatPassword = updatePwdParams.get("re_pwd");
        if (!StringUtils.hasLength(oldPassword) || !StringUtils.hasLength(newPassword) || !StringUtils.hasLength(repeatPassword))
            return Result.error("缺少参数");
        Map<String, Object> claims = ThreadLocalUtil.get();
        User user = userService.findByUserName((String) claims.get("username"));
        if (!Md5Util.checkPassword(oldPassword, user.getPassword()))
            return Result.error("原密码错误");
        if (!repeatPassword.equals(newPassword))
            return Result.error("两次新密码不一致");
        user.setPassword(Md5Util.getMD5String(newPassword));
        userService.updateById(user);

        // 密码修改成功后，删除 Redis 中的 token
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.getOperations().delete(jwtToken);

        return Result.success("更新密码成功");
    }
}