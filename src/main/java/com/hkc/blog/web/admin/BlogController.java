package com.hkc.blog.web.admin;

import com.hkc.blog.po.Blog;
import com.hkc.blog.service.BlogService;
import com.hkc.blog.service.TypeService;
import com.hkc.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author 胡开成
 * @Date 2020/3/1 14:26
 */
@Controller
@RequestMapping("/admin")
public class BlogController {



    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    /*
     * @Author hkc
     * @Date 16:33 2020/3/3
     * @info 用于查博客列表
     **/

    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 4,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        BlogQuery blog, Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return "admin/blogs";
    }

    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 4,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                         BlogQuery blog, Model model){
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return "admin/blogs :: blogList";
    }

    public String input(){
        return "admin/blogs-input";
    }

}
