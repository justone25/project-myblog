package com.xlydbb.myblog.web;

import com.xlydbb.myblog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ArchiveBlogController {
    @Autowired
    BlogService blogService;
    @GetMapping("/archives")
    public String archives(Model model){
        model.addAttribute("archiveMap",blogService.listByYear());
        model.addAttribute("blogCount",blogService.countBlog());
        return "archives";
    }
    @GetMapping("/footer/newBlog")
    public String newBlogs(Model model){
        model.addAttribute("blogList",blogService.listRecommenBlogTop(3));
        return "_fragments :: newBlogList";
    }

}
