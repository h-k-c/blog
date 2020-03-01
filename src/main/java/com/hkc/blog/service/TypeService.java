package com.hkc.blog.service;

import com.hkc.blog.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @Author 胡开成
 * @Date 2020/3/1 16:49
 */
public interface TypeService {
    Type saveType(Type type);

    Type getType(Long id);

    Page<Type> listType(Pageable pageable);

    Type updateType(Long id,Type type);

    void deleteType(Long id);
}
