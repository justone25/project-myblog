package com.xlydbb.myblog.web;

import com.xlydbb.myblog.pojo.BlogTag;
import com.xlydbb.myblog.service.BlogService;
import com.xlydbb.myblog.service.TagService;
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
public class TagBlogController {
    @Autowired
    TagService tagService;
    @Autowired
    BlogService blogService;

    @GetMapping("/tags/{id}")
    public String tags(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC)Pageable pageable,
                       @PathVariable Long id,Model model){
        List<BlogTag> blogTags = tagService.listTopTag(10000);
        if(id == -1){
            id = blogTags.get(0).getId();
        }
        model.addAttribute("activeTag",id);
        model.addAttribute("page",blogService.listPublishedAndTagBlog(pageable,id));
        model.addAttribute("tags",blogTags);
        return "tags";
    }
}
