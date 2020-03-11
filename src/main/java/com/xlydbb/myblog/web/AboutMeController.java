package com.xlydbb.myblog.web;

import com.xlydbb.myblog.service.TagService;
import com.xlydbb.myblog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutMeController {
    @Autowired
    TagService tagService;
    @Autowired
    TypeService typeService;
    @GetMapping("/about")
    public String about(Model model){
        return "about";
    }
}
