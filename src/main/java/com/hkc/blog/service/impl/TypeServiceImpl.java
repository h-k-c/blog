package com.hkc.blog.service.impl;

import com.hkc.blog.dao.TypeRepository;
import com.hkc.blog.exception.NotFoundException;
import com.hkc.blog.po.Type;
import com.hkc.blog.service.TypeService;
import com.sun.istack.Pool;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author 胡开成
 * @Date 2020/3/1 16:57
 */
@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private TypeRepository typeRepository;

    @Transactional
    @Override
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }

    @Transactional
    @Override
    public Type getType(Long id) {
        return typeRepository.getOne(id);
    }

    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    @Override
    public Type getTypeByName(String name) {

        return typeRepository.findByName(name);
    }

    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Type t=typeRepository.getOne(id);
        if(t==null){
            throw new NotFoundException("不存在该类型");
        }
        //对象进行复制
        BeanUtils.copyProperties(type,t);
        return typeRepository.save(t);
    }

    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }
    /*
    * @Author hkc
    * @Date 22:22 2020/3/4
    * @info 按照大小来进行排序
    **/
    @Override
    public List<Type> listTypeTop(Integer size) {
        Sort sort =Sort.by(Sort.Direction.DESC, "blogs.size");
        Pageable pageable = PageRequest.of(0, size, sort);
        return typeRepository.findTop(pageable);
    }

    @Transactional
    @Override
    public void deleteType(Long id) {
        typeRepository.deleteById(id);
    }
}
