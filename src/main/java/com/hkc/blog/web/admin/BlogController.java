package com.hkc.blog.web.admin;

import com.hkc.blog.po.Blog;
import com.hkc.blog.po.Tag;
import com.hkc.blog.po.Type;
import com.hkc.blog.po.User;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Author 胡开成
 * @Date 2020/3/1 14:26
 */
@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String INPUT="admin/blogs-input";
    private static final String LIST="admin/blogs";
    private static final String REDIRECT_LIST="redirect:/admin/blogs";


    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    /*
     * @Author hkc
     * @Date 16:33 2020/3/3
     * @info 用于查博客列表
     **/

    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        BlogQuery blog, Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return LIST;
    }

    /*
    * @Author hkc
    * @Date 16:41 2020/3/4
    * @info 用于博客查询
    **/

    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                         BlogQuery blog, Model model){
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return "admin/blogs :: blogList";
    }

    /*
    * @Author hkc
    * @Date 16:41 2020/3/4
    * @info 博客新增
    **/
    @GetMapping("/blogs/input")
    public String input(Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
        model.addAttribute("blog",new Blog());
        return INPUT;
    }

    @GetMapping("/blogs/{id}/input")
    public String editinput(@PathVariable("id") Long id, Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());

        Blog blog=blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog",blog);
        return INPUT;
    }

    /*
    * @Author hkc
    * @Date 16:42 2020/3/4
    * @info 新增博客
    **/
    @PostMapping("/blogs")
    public String post(Blog blog, HttpSession session, RedirectAttributes attributes){
        User user = (User) session.getAttribute("user");
        blog.setUser(user);

        Type type = typeService.getType(blog.getType().getId());
        blog.setType(type);

        List<Tag> tags = tagService.listTag(blog.getTagIds());
        blog.setTags(tags);

        Blog b = new Blog();
        //如果没传id就是新增 否则是更新
        if(blog.getId() == null){
            b.setShareStatement(true);
            b = blogService.saveBlog(blog);
        } else {
            b = blogService.updateBlog(blog.getId(), blog);
        }

        if(b == null){
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return REDIRECT_LIST;
    }

    /*
    * @Author hkc
    * @Date 20:48 2020/3/4
    * @info 删除博客
    **/
    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable("id")Long id,RedirectAttributes attributes){
        blogService.delete(id);
        attributes.addFlashAttribute("message","删除成功");
        return REDIRECT_LIST;
    }

}
