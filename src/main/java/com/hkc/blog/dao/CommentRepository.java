package com.hkc.blog.dao;

import com.hkc.blog.po.Blog;
import com.hkc.blog.po.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author 胡开成
 * @Date 2020/3/5 18:30
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findCommentListByBlogIdAndParentCommentIsNull(Long id,Sort sort);


}
