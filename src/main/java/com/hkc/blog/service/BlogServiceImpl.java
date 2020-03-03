package com.hkc.blog.service;

import com.hkc.blog.dao.BlogRepository;
import com.hkc.blog.exception.NotFoundException;
import com.hkc.blog.po.Blog;
import com.hkc.blog.po.Type;
import com.hkc.blog.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author 胡开成
 * @Date 2020/3/3 15:00
 */
@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogRepository blogRepository;

    @Override
    public Blog getBlog(Long id) {
        return blogRepository.getOne(id);
    }

    /*
    * @Author hkc
    * @Date 15:29 2020/3/3
    * @info specification用于动态查询
    **/

    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {

        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates=new ArrayList<>();
                if(!"".equals(blog.getTitle())&&blog.getTitle()!=null){
                    predicates.add(cb.like(root.<String>get("title"),"%"+blog.getTitle()+"%"));
                }
                if(blog.getTypeId()!=null){
                    predicates.add(cb.equal(root.<Type>get("type").get("id"),blog.getTypeId()));
                }
                if(blog.isRecommend()){
                    predicates.add(cb.equal(root.<Boolean>get("recommend"),blog.isRecommend()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }

    @Override
    public Blog saveBlog(Blog blog) {
        return blogRepository.save(blog);
    }
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b=blogRepository.getOne(id);
        if(b==null){
            throw new NotFoundException("不存在这个博客");
        }else{
            BeanUtils.copyProperties(blog,b);
            return blogRepository.save(b);
        }
    }

    @Override
    public void delete(Long id) {
        blogRepository.deleteById(id);
    }
}
