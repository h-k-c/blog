package com.hkc.blog.web;

import com.hkc.blog.po.Blog;
import com.hkc.blog.po.Tag;
import com.hkc.blog.po.Type;
import com.hkc.blog.service.BlogService;
import com.hkc.blog.service.TagService;
import com.hkc.blog.service.TypeService;
import com.hkc.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @Author 胡开成
 * @Date 2020/3/6 0:06
 */
@Controller
public class TypeShowController {
    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;

    @GetMapping("types/{id}")
    public String types(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        @PathVariable Long id, Model model){
        List<Type> types = typeService.listTypeTop(100);
        if(id == -1){
            id = types.get(0).getId();
        }
        BlogQuery blogVO = new BlogQuery();
        blogVO.setTypeId(id);
        model.addAttribute("types", types);
        model.addAttribute("page", blogService.listBlog(pageable, blogVO));
        model.addAttribute("activeTypeId", id);
        return "types";
    }

}
