package com.hkc.blog.dao;

import com.hkc.blog.po.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author 胡开成
 * @Date 2020/3/2 21:22
 */
public interface TagRepository extends JpaRepository<Tag,Long> {
    Tag findByName(String name);
}
