package com.xlydbb.myblog.web.admin;

import com.xlydbb.myblog.pojo.BlogTag;
import com.xlydbb.myblog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class BlogTagController {
    @Autowired
    TagService tagService;
    @RequestMapping("/tags")
    public String tags(@PageableDefault(size = 10,sort = {"id"},
            direction = Sort.Direction.DESC)Pageable pageable,Model model) {
        model.addAttribute("page", tagService.listTag(pageable));
        return "admin/tags";
    }
    @GetMapping("/tags/input")
    public String input(Model model){
        model.addAttribute("blogTag",new BlogTag());
        return "admin/tags-input";
    }
    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id,Model model){
        model.addAttribute("blogTag",tagService.getTag(id));
        return "admin/tags-input";
    }
    @PostMapping("/tags")
    public String save(@Valid BlogTag blogTag, BindingResult bindingResult,
                       RedirectAttributes redirectAttributes){
        BlogTag blogTagDB = tagService.getTagByName(blogTag.getName());
        if(blogTagDB != null){
            bindingResult.rejectValue("message","nameError","不能重复添加标签名称");
        }
        if(bindingResult.hasErrors()){
            return "admin/tags-input";
        }
        BlogTag bt = tagService.saveTag(blogTag);
        if(bt != null){
            redirectAttributes.addFlashAttribute("message","新增成功");
        }else{
            redirectAttributes.addFlashAttribute("message","新增失败");
        }
        return "redirect:/admin/tags";
    }
    @PostMapping("/tags/{id}")
    public String editPost(@Valid BlogTag blogTag, BindingResult bindingResult,
                       @PathVariable Long id, RedirectAttributes redirectAttributes){
        BlogTag blogTagDB = tagService.getTagByName(blogTag.getName());
        if(blogTagDB != null){
            bindingResult.rejectValue("message","nameError","不能重复添加标签名称");
        }
        if(bindingResult.hasErrors()){
            return "admin/tags-input";
        }
        BlogTag bt = tagService.updateTag(id,blogTag);
        if(bt != null){
            redirectAttributes.addFlashAttribute("message","修改成功");
        }else{
            redirectAttributes.addFlashAttribute("message","修改失败");
        }
        return "redirect:/admin/tags";
    }
    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes redirectAttributes){
        BlogTag bt = tagService.deleteTag(id);
        redirectAttributes.addFlashAttribute("message","删除[分类:"+bt.getName()+"]成功！");
        return "redirect:/admin/tags";
    }
}
