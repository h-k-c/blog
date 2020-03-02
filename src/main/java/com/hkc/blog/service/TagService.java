package com.hkc.blog.service;

import com.hkc.blog.po.Tag;
import com.hkc.blog.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @Author 胡开成
 * @Date 2020/3/2 21:21
 */
public interface TagService {
    Tag getTagById(Long id);

    Tag saveTag(Tag tag);

    Page<Tag> listTag(Pageable pageable);

    Tag getTagByName(String name);

    Tag updateTag(Long id,Tag tag);

    void deleteTag(Long id);
}
