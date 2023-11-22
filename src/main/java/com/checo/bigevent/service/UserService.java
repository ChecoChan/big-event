package com.checo.bigevent.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.checo.bigevent.entity.User;

public interface UserService extends IService<User> {
    User findByUserName(String username);
    void register(String username, String password);

    void updateAvatar(String avatarUrl);
}
