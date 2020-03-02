package com.hkc.blog.web.admin;

import com.hkc.blog.po.Type;
import com.hkc.blog.service.TypeService;
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
 * @Date 2020/3/1 17:28
 */
@Controller
@RequestMapping("/admin")
public class TypeController {
    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    public String types(@PageableDefault(size = 6,sort = {"id"},
                        direction = Sort.Direction.DESC) Pageable pageable,
                        Model model){
        System.out.println(typeService.listType(pageable));
        model.addAttribute("page",typeService.listType(pageable));
        return "admin/types";
    }
    /*
    * @Author hkc
    * @Date 0:59 2020/3/3
    * @info 用于新增响应按钮
    **/

    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type",new Type());
        return "admin/types-input";
    }
    /*
    * @Author hkc
    * @Date 20:48 2020/3/2
    * @info 用于修改标签页面
    **/

    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable("id") Long id, Model model){
        model.addAttribute("type",typeService.getType(id));
        return "admin/types-input";
    }

    /*
    * @Author hkc
    * @Date 14:31 2020/3/2
    * @info 新建进行保存，因为是重定向所以要用attributes
    **/
    @PostMapping("/types")
    public String post(@Valid Type type,BindingResult result,RedirectAttributes attributes){
        Type type1=typeService.getTypeByName(type.getName());
        if(type1!=null){
            result.rejectValue("name","nameError","不能添加重复的分类");
        }
        if(result.hasErrors()){
            return "admin/types-input";
        }
        Type t=typeService.saveType(type);
        if(t==null){
            attributes.addFlashAttribute("message","操作失败");
        }else{
            attributes.addFlashAttribute("message","操作成功");
        }
        return "redirect:/admin/types";
    }
    /*
     * @Author hkc
     * @Date 17:29 2020/3/2
     * @info 验证必须和BindingResult绑在一起
     **/

    /*
    * @Author hkc
    * @Date 20:48 2020/3/2
    * @info 用于更新标签
    **/

    @PostMapping("/types/{id}")
    public String editPost(@Valid Type type,BindingResult result,
                           @PathVariable Long id,RedirectAttributes attributes){
        Type type1=typeService.getTypeByName(type.getName());
        if(type1!=null){
            result.rejectValue("name","nameError","不能添加重复的分类");
        }
        if(result.hasErrors()){
            return "admin/types-input";
        }
        Type t=typeService.updateType(id,type);
        if(t==null){
            attributes.addFlashAttribute("message","更新失败");
        }else{
            attributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/types";
    }

    /*
    * @Author hkc
    * @Date 20:57 2020/3/2
    * @info 处理标签的删除操作
    **/
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable("id") Long id,RedirectAttributes redirectAttributes){
        typeService.deleteType(id);
        redirectAttributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/types";
    }

}
