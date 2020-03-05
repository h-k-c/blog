package com.hkc.blog.service;

import com.hkc.blog.dao.BlogRepository;
import com.hkc.blog.exception.NotFoundException;
import com.hkc.blog.po.Blog;
import com.hkc.blog.po.Type;
import com.hkc.blog.util.MarkDownUtils;
import com.hkc.blog.util.MyBeanUtils;
import com.hkc.blog.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.*;

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

    @Transactional
    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Join join = root.join("tags");
                return builder.equal(join.get("id"), tagId);
            }
        }, pageable);
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        if(blog.getId()==null){
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        }else{
            blog.setUpdateTime(new Date());
        }
        return blogRepository.save(blog);
    }
    @Override
    @Transactional
    public Blog updateBlog(Long id, Blog blog) {
        Blog dbBlog = blogRepository.findById(id).get();
        if (dbBlog == null) {
            throw new NotFoundException("博客找不到了");
        }
        //MyBeanUtils.getNullPropertyNames过滤为空的字段
        BeanUtils.copyProperties(blog, dbBlog, MyBeanUtils.getNullPropertyNames(blog));
        dbBlog.setUpdateTime(new Date());
        return blogRepository.save(dbBlog);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {

        return blogRepository.findByQuery(query,pageable);
    }

    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        Sort sort=Sort.by(Sort.Direction.DESC,"updateTime");
        Pageable pageable= PageRequest.of(0,size,sort);
        return blogRepository.findTop(pageable);
    }


    @Transactional
    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = blogRepository.findGroupYear();
        Map<String, List<Blog>> map = new HashMap<>();
        for (String year : years) {
            map.put(year, blogRepository.findByYear(year));
        }
        return map;
    }

    @Override
    public Long countBlog() {
        return blogRepository.count();
    }

    @Transactional
    @Override
    public Blog getAndConvert(Long id) {
        Blog blog=blogRepository.getOne(id);
        if(blog==null){
            throw new NotFoundException("博客不存在");
        }
        Blog b=new Blog();
        BeanUtils.copyProperties(blog,b);
        String contnet=b.getContent();
        b.setContent(MarkDownUtils.markdownToHtmlExtensions(contnet));

        //更新views
        blogRepository.updateViews(id);
        return b;
    }


    @Transactional
    @Override
    public void delete(Long id) {
        blogRepository.deleteById(id);
    }
}
