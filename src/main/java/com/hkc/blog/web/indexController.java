package com.hkc.blog.web;

import com.hkc.blog.exception.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
/*
* @Author hkc
* @Description //TODO
* @Date 20:55 2020/2/28
* @Param
* @return
**/

@Controller
public class indexController {

    @GetMapping("/")
    public String index(){
////        int i=9/0;
//        String blog=null;
//        if(blog==null){
//            throw  new NotFoundException("博客没找到");
//        }
        System.out.println("-------index-------------");
        return "index";
    }
    
}
