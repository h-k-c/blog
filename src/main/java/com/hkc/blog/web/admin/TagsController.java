package com.hkc.blog.web.admin;

import com.hkc.blog.po.Tag;
import com.hkc.blog.service.TagService;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * @Author 胡开成
 * @Date 2020/3/2 21:31
 */
@Controller
@RequestMapping("/admin")
public class TagsController {
     @Autowired
     private TagService tagService;

     /*
     * @Author hkc
     * @Date 0:04 2020/3/3
     * @info 用于显示所有标签
     **/

     @GetMapping("/tags")
     public String tags(@PageableDefault(size = 5,sort = {"id"},
             direction = Sort.Direction.DESC) Pageable pageable, Model model){
          model.addAttribute("page",tagService.listTag(pageable));
          return "/admin/tags";
     }

     @GetMapping("/tags/input")
     public String tags(Model model){
          model.addAttribute("tag",new Tag());
          return "/admin/tags-input";
     }

     /*
     * @Author hkc
     * @Date 0:04 2020/3/3
     * @info 新增tag，跳转到修改页面，并带入对象
     **/

     @GetMapping("/tags/{id}/input")
     public String input(@PathVariable("id") Long id, Model model){
          model.addAttribute("tag",tagService.getTagById(id));
          return "admin/tags-input";
     }

     /*
     * @Author hkc
     * @Date 0:04 2020/3/3
     * @info 用于新增后保存页面。
     **/


     @PostMapping("/tags")
     public String post(@Valid Tag tag, BindingResult result, RedirectAttributes attributes){
          Tag tag1=tagService.getTagByName(tag.getName());
          if(tag1!=null){
               result.rejectValue("name","nameError","不能添加重复的分类");
          }
          if(result.hasErrors()){
               return "admin/tags-input";
          }
          Tag t=tagService.saveTag(tag);
          if(t==null){
               attributes.addFlashAttribute("message","新增失败");
          }else{
               attributes.addFlashAttribute("message","新增成功");
          }
          return "redirect:/admin/tags";
     }

     @PostMapping("/tags/{id}")
     public String editpost(@Valid Tag tag, BindingResult result,
                            RedirectAttributes attributes, @PathVariable Long id){
          Tag tag1=tagService.getTagByName(tag.getName());
          if(tag1!=null){
               result.rejectValue("name","nameError","不能添加重复的分类");
          }
          if(result.hasErrors()){
               return "admin/tags-input";
          }
          Tag t=tagService.updateTag(id,tag);
          if(t==null){
               attributes.addFlashAttribute("message","更新失败");
          }else{
               attributes.addFlashAttribute("message","更新成功");
          }
          return "redirect:/admin/tags";
     }

     /*
     * @Author hkc
     * @Date 1:12 2020/3/3
     * @info 删除标签
     **/

     @GetMapping("/tags/{id}/delete")
     public String delete(@PathVariable("id") Long id,RedirectAttributes attributes){
          tagService.deleteTag(id);
          attributes.addFlashAttribute("message","删除成功");
          return "redirect:/admin/tags";
     }
}
