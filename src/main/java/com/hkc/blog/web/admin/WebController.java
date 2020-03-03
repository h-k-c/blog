package com.hkc.blog.web.admin;

import com.hkc.blog.dao.TypeRepository;
import com.hkc.blog.po.Blog;
import com.hkc.blog.service.BlogService;
import com.hkc.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author 胡开成
 * @Date 2020/3/3 15:57
 */
@Controller
@RequestMapping("/admin")
public class WebController {

}
