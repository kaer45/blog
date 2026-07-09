
package com.example.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.blog.entity.User;

public interface UserService extends IService<User> {
    User findByUsername(String username);
    User findByEmail(String email);
    User register(User user);
    User login(String username, String password);
}
