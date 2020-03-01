package com.hkc.blog.service;

import com.hkc.blog.dao.UserRepository;
import com.hkc.blog.po.User;
import com.hkc.blog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author 胡开成
 * @Date 2020/2/29 18:22
 */
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Override
    public User check(String username, String password) {
        User user=userRepository.findByUsernameAndPassword(username,password);
        return user;
    }
}
