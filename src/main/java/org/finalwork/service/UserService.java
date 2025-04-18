package org.finalwork.service;

import org.finalwork.pojo.User;

public interface UserService {
    // 根据用户名查询用户
    User findByUserName(String username);

    void register(String username, String password);

    // 更新用户数据
    void update(User user);

    // 更新头像
    void updateAvatar(String avatarUrl);

    void updatePwd(String newPwd);
}
