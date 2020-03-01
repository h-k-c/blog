package com.hkc.blog.web.admin;

import com.hkc.blog.po.User;
import com.hkc.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @Author 胡开成
 * @Date 2020/2/29 18:28
 */

@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String login(){
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session, RedirectAttributes attributes){
        System.out.println("进入-----------------------------------");
        User user=userService.check(username, password);
        System.out.println("成功-----------------------------------");
        if(user!=null){
            user.setPassword(null);
            session.setAttribute("user",user);
            System.out.println("登陆成功-----------------------------------");
            return "admin/index";
        }else{
            System.out.println("登陆shibai-----------------------------------");
            attributes.addFlashAttribute("message","用户名或密码错误");
            return "redirect:/admin";
        }
    }
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
