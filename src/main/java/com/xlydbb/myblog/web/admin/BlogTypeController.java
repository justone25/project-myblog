package com.xlydbb.myblog.web.admin;

import com.xlydbb.myblog.pojo.BlogType;
import com.xlydbb.myblog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class BlogTypeController {
    @Autowired
    TypeService typeService;
    @GetMapping("/types")
    public String types(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC)
                                    Pageable pageable,Model model){
        model.addAttribute("page",typeService.listType(pageable));
        return "admin/types";
    }
    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("blogType",new BlogType());
        return "admin/types-input";
    }
    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("blogType",typeService.getType(id));
        return "admin/types-input";
    }
    //新增表单提交
    @PostMapping("/types")
    public String save(@Valid BlogType blogType, BindingResult bindingResult,
                       RedirectAttributes redirectAttributes){
        BlogType blogTypeDB = typeService.getTypeByName(blogType.getName());
        if(blogTypeDB != null){
            bindingResult.rejectValue("name","nameError","不能重复添加分类名称");
        }
        if(bindingResult.hasErrors()){
            return "admin/types-input";
        }
        BlogType type = typeService.saveType(blogType);
        if(type == null){
            //新增失败
            redirectAttributes.addFlashAttribute("message","新增失败");
        }else{
            //新增成功
            redirectAttributes.addFlashAttribute("message","新增成功");
        }
        return "redirect:/admin/types";
    }
    //修改表单提交
    @PostMapping("/types/{id}")
    public String editPost(@Valid BlogType blogType, BindingResult bindingResult,
                       @PathVariable Long id, RedirectAttributes redirectAttributes){
        BlogType blogTypeDB = typeService.getTypeByName(blogType.getName());
        if(blogTypeDB != null){
            bindingResult.rejectValue("name","nameError","不能添加重复分类名称");
        }
        if(bindingResult.hasErrors()){
            return "admin/types-input";
        }
        BlogType type = typeService.updateType(id,blogType);
        if(type == null){
            //新增失败
            redirectAttributes.addFlashAttribute("message","更新失败");
        }else{
            //新增成功
            redirectAttributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/types";
    }
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes){
        BlogType blogTypeDB = typeService.deleteType(id);
        redirectAttributes.addFlashAttribute("message","删除[分类:"+blogTypeDB.getName()+"]成功！");
        return "redirect:/admin/types";
    }
}
