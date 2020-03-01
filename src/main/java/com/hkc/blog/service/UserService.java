package com.hkc.blog.service;

import com.hkc.blog.po.User;

/**
 * @Author 胡开成
 * @Date 2020/2/29 18:21
 */
public interface UserService {

    User check(String username,String password);

}
