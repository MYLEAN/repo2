package com.zq.blog.web.admin;

import com.zq.blog.po.Type;
import com.zq.blog.service.TypeService;
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
public class TypeController {

    @Resource
    private TypeService typeService;

    @GetMapping("/types")//首页点击分类进入
    public String types(@PageableDefault(size = 4,sort = ("id"),direction = Sort.Direction.ASC) Pageable pageable,
                        Model model){
        model.addAttribute("page",typeService.listType(pageable));
        return "admin/types";
    }

    @GetMapping("/types/input")//点击新增进入新增页面
    public String Input(Model model){
        model.addAttribute("type",new Type());
        return "admin/types-input";
    }


    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("type",typeService.getType(id));
        return "/admin/types-input";
    }

    @PostMapping("/types")//添加type类跳转
    public String input(@Valid Type type,BindingResult result, RedirectAttributes redirectAttributes){
        Type type1=typeService.getByName(type.getName());
        if (type1 != null) {
            result.rejectValue("name","nameError","分类名不能重复添加");
        }
        if (result.hasErrors()) {
            return "admin/types-input";
        }
        Type t=typeService.saveType(type);
        if (t == null) {
            redirectAttributes.addFlashAttribute("message","增加失败");
        }else {
            redirectAttributes.addFlashAttribute("message","增加成功");
        }
        return "redirect:/admin/types";
    }

    /**
     * 备注：这里一个@Valid的参数后必须紧挨着一个BindingResult 参数，
     * 否则spring会在校验不通过时直接抛出异常，BindingResult是springmvc的一个验证框架。
     *
     * 是对该Type 实体进行校验，在Type类中的属性上使用spring的注解
     */
    @PostMapping("/types/{id}")//修改type名称跳转
    public String editpost(@Valid Type type,BindingResult result,@PathVariable Long id, RedirectAttributes redirectAttributes){
        Type type1=typeService.getByName(type.getName());
        if (type1 != null) {
            result.rejectValue("name","nameError","分类名不能重复添加");
        }
        if (result.hasErrors()) {
            return "admin/types-input";
        }
        Type t=typeService.updateType(id,type);
        if (t == null) {
            redirectAttributes.addFlashAttribute("message","修改失败");
        }else {
            redirectAttributes.addFlashAttribute("message","修改成功");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/types";
    }
}
