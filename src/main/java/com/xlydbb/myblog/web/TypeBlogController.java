package com.xlydbb.myblog.web;

import com.xlydbb.myblog.pojo.BlogType;
import com.xlydbb.myblog.service.BlogService;
import com.xlydbb.myblog.service.TypeService;
import com.xlydbb.myblog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@Controller
public class TypeBlogController {
    @Autowired
    TypeService typeService;
    @Autowired
    BlogService blogService;
    @GetMapping("/types/{id}")
    public String types(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        @PathVariable Long id, Model model){
        List<BlogType> blogTypes = typeService.listTopType(10000);
        if(id == -1){
            id = blogTypes.get(0).getId();
        }
        model.addAttribute("types",blogTypes);
        model.addAttribute("page",blogService.listPublishedAndTypeBlog(pageable,id));
        model.addAttribute("activeType",id);
        return "types";
    }
}
