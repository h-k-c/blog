package com.hkc.blog;

import com.hkc.blog.po.Blog;
import com.hkc.blog.service.BlogService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.annotation.WebServlet;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebServlet
class BlogApplicationTests {

    @Autowired
    private BlogService blogService;

    @Test
    public void findAll(){

    }


}
