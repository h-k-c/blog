package com.hkc.blog.service;

import com.hkc.blog.po.Comment;

import java.util.List;

/**
 * @Author 胡开成
 * @Date 2020/3/5 18:23
 */
public interface CommentService {

    List<Comment> listCommentByBlogId(Long id);

    Comment saveComment(Comment comment);
}
