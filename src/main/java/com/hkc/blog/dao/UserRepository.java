package com.hkc.blog.dao;

import com.hkc.blog.po.User;
import org.springframework.data.jpa.repository.JpaRepository;
/*
* @Author hkc
* @Date 18:25 2020/2/29
* @return
**/
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsernameAndPassword(String username,String password);
}
