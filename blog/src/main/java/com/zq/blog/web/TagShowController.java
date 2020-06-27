package com.zq.blog.web;

import com.zq.blog.po.Tag;
import com.zq.blog.service.BlogService;
import com.zq.blog.service.TagService;
import com.zq.blog.vo.BlogQuery;
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
public class TagShowController {

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogService blogService;

    /*从首页点击进入分类页面，点击进去默认选中的是分类的第一个*/
    @GetMapping("/tags/{id}")
    public String types(@PageableDefault(size = 4,sort = {"updateTime"},direction = Sort.Direction.ASC) Pageable pageable,
                        Model model, @PathVariable Long id){
        List<Tag> tagList=tagService.listTagTop(1000);
        //默认传过来的id是-1
        if (id == -1) {
            id=tagList.get(0).getId();//取第一个type
        }
        //所有的分类
        model.addAttribute("tags",tagList);
        //根据分类查询的博客列表
        model.addAttribute("page",blogService.listBlog(id,pageable));
        model.addAttribute("activeTagId",id);
        return "tags";
    }
}
