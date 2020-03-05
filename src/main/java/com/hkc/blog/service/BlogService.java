package com.hkc.blog.service;

import com.hkc.blog.po.Blog;
import com.hkc.blog.po.Type;
import com.hkc.blog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @Author 胡开成
 * @Date 2020/3/3 14:39
 */
public interface BlogService {

    Page<Blog> listBlog(Pageable pageable);

    Page<Blog> listBlog(String query,Pageable pageable);

    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    Page<Blog> listBlog(Long tagId,Pageable pageable);

    Blog getBlog(Long id);

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id,Blog blog);

    Blog getAndConvert(Long id);

    List<Blog> listRecommendBlogTop(Integer id);

    Map<String,List<Blog>> archiveBlog();

    Long countBlog();

    void delete(Long id);

}
