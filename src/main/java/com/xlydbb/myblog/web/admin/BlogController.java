package com.xlydbb.myblog.web.admin;

import com.xlydbb.myblog.pojo.Blog;
import com.xlydbb.myblog.pojo.User;
import com.xlydbb.myblog.service.BlogService;
import com.xlydbb.myblog.service.TagService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class BlogController {
    private static final String INPUT = "admin/blog-input";
    private static final String LIST = "admin/blog";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";

    @Autowired
    BlogService blogService;
    @Autowired
    TypeService typeService;
    @Autowired
    TagService tagService;
    @GetMapping("/blogs")
    public String list(@PageableDefault(size = 2,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                       BlogQuery blog, Model model){
        model.addAttribute("page",blogService.listBlog(pageable, blog));
        model.addAttribute("types",typeService.listType());
        return LIST;
    }
    @PostMapping("/blogs/search")
    public String listSearch(@PageableDefault(size = 2,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                             BlogQuery blog, Model model){
        model.addAttribute("page",blogService.listBlog(pageable, blog));
        return "admin/blog :: blogList";
    }
    @GetMapping("/blogs/input")
    public String input(Model model){
        model.addAttribute("blog",new Blog());
        model.addAttribute("tags",tagService.listTag());
        model.addAttribute("types",typeService.listType());
        return INPUT;
    }
    //新增or修改blog
    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes redirectAttributes, HttpSession session){
        blog.setUser((User) session.getAttribute("user"));
        blog.setBlogType(typeService.getType(blog.getBlogType().getId()));
        blog.setBlogTags(tagService.listTag(blog.getTagIds()));
        Blog b;
        if(blog.getId() == null){
            b = blogService.saveBlog(blog);
        }else{
            b = blogService.updateBlog(blog.getId(),blog);
        }
        if(b == null){
            redirectAttributes.addFlashAttribute("message","操作失败");
        }else{
            redirectAttributes.addFlashAttribute("message","操作成功");
        }
        return REDIRECT_LIST;
    }
    //去往修改页面
    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        Blog blog = blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog",blog);
        model.addAttribute("tags",tagService.listTag());
        model.addAttribute("types",typeService.listType());
        return INPUT;
    }
    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes redirectAttributes){
        Blog blog = blogService.deleteBlog(id);
        redirectAttributes.addFlashAttribute("message","删除[博客:"+blog.getTitle()+"]成功！");
        return REDIRECT_LIST;
    }
}
