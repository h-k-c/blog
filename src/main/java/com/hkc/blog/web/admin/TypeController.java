package com.hkc.blog.web.admin;

import com.hkc.blog.po.Type;
import com.hkc.blog.service.TypeService;
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
    @GetMapping("/types/input")
    public String input(){
        return "admin/types-input";
    }

    @PostMapping("/types")
    public String post(Type type){
        Type t=typeService.saveType(type);
        if(t==null){

        }else{

        }
        return "redirect:/admin/types";
    }
}
