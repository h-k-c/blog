package com.hkc.blog.service;

import com.hkc.blog.po.Blog;
import com.hkc.blog.po.Type;
import com.hkc.blog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Author 胡开成
 * @Date 2020/3/3 14:39
 */
public interface BlogService {

    Blog getBlog(Long id);

    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id,Blog blog);

    void delete(Long id);

}
