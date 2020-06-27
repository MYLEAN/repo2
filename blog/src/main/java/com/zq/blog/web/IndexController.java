package com.zq.blog.web;

import com.zq.blog.NotFoundException;
import com.zq.blog.po.Blog;
import com.zq.blog.service.BlogService;
import com.zq.blog.service.TagService;
import com.zq.blog.service.TypeService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller
public class IndexController {

    @Resource
    private BlogService blogService;

    @Resource
    private TypeService typeService;

    @Resource
    private TagService tagService;

    @GetMapping("/")
    public String index(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, Model model){
        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types",typeService.listTypeTop(6));
        model.addAttribute("tags",tagService.listTagTop(10));
        model.addAttribute("reconmmendBlogs",blogService.listReconmmendBlogTop(8));
        return "index";
    }

    @PostMapping("/search")
    public String  search(@PageableDefault(size = 4,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                          @RequestParam String query, Model model){
        model.addAttribute("page",blogService.listBlog("%"+query+"%", pageable));
        model.addAttribute("query",query);//查询打开一个新的页面之后，搜索框中返回查询的内容
        return "search";
    }

    @GetMapping("/blog/{id}")/*进入博客详情页面*/
    public String blog(@PathVariable Long id, Model model, Blog blog){
        Blog b=blogService.getBlog(id);
        model.addAttribute("blog",blogService.getAndconvert(id));
        return "blog";
    }

    @GetMapping("/login")
    public String tags(){
        return "admin/login";
    }
}
