package com.zq.blog.web.admin;

import com.zq.blog.dao.TagRepository;
import com.zq.blog.po.Tag;
import com.zq.blog.service.TagService;
import com.zq.blog.service.impl.TagServiceImpl;
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

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TagController {

    @Resource
    private TagService tagService;

    @GetMapping("/tags")//首页点击标签进入
    public String tags(@PageableDefault(size = 4,sort = ("id"),direction = Sort.Direction.ASC)
                                   Pageable pageable, Model model){
        model.addAttribute("page",tagService.list(pageable));
        return "admin/tags";
    }

    @GetMapping("/tags/input")//tags中点击新增进入
    public String tagsInput(Model model){
        model.addAttribute("tag",new Tag());
        return "admin/tags-input";
    }

    @PostMapping("/tags")//新增提交后返回列表
    public String tagspost(@Valid Tag tag,BindingResult result, RedirectAttributes attributes){
        Tag t=tagService.getByname(tag.getName());
        if(t!=null){
            result.rejectValue("name","nameError","不能重复添加");
        }
        if (result.hasErrors()) {
            return "/admin/tags-input";
        }
        Tag tag1=tagService.save(tag);
        if (tag1 == null) {
            attributes.addFlashAttribute("message","增加失败");
        }
        else {
            attributes.addFlashAttribute("message","增加成功");
        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/{id}/input")//点击编辑进入
    public String edit(@PathVariable Long id,Model model){
        model.addAttribute("tag",tagService.getTag(id));
        return "admin/tags-input";
    }

    @PostMapping("/tags/{id}")
    public String editpost(@Valid Tag tag, BindingResult result, @PathVariable Long id, RedirectAttributes attributes){
        Tag t=tagService.getByname(tag.getName());
        if (t != null) {
            result.rejectValue("name","nameError","不能重复添加标签");
        }
        if (result.hasErrors()) {
            return "admin/tags-input";
        }
        Tag tag1=tagService.update(id,tag);
        if (tag1 == null) {
            attributes.addFlashAttribute("message","修改失败");
        }else {
            attributes.addFlashAttribute("message","修改成功");
        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        tagService.delete(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/tags";
    }
}
