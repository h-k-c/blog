package com.hkc.blog.service;

import com.hkc.blog.dao.TypeRepository;
import com.hkc.blog.exception.NotFoundException;
import com.hkc.blog.po.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @Author 胡开成
 * @Date 2020/3/1 16:57
 */
@Service
public class TypeServiceImpl implements TypeService {

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

    @Transactional
    @Override
    public void deleteType(Long id) {
        typeRepository.deleteById(id);
    }
}
