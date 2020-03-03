package com.hkc.blog.service;

import com.hkc.blog.dao.TagRepository;
import com.hkc.blog.exception.NotFoundException;
import com.hkc.blog.po.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @Author 胡开成
 * @Date 2020/3/2 21:22
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Override
    public Tag getTagById(Long id) {
        return tagRepository.getOne(id);
    }

    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag tag1=tagRepository.getOne(id);
        if(tag1==null){
            throw new NotFoundException("不存在该标签");
        }
        BeanUtils.copyProperties(tag,tag1);
        return tagRepository.save(tag1);
    }

    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }
}