package com.zq.blog.web;

import com.zq.blog.po.Blog;
import com.zq.blog.po.Type;
import com.zq.blog.service.BlogService;
import com.zq.blog.service.TypeService;
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
public class TypeShowController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;

    /*从首页点击进入分类页面，点击进去默认选中的是分类的第一个*/
    @GetMapping("/types/{id}")
    public String types(@PageableDefault(size = 4,sort = {"updateTime"},direction = Sort.Direction.ASC) Pageable pageable,
                        Model model, @PathVariable Long id){
        List<Type> typeList=typeService.listTypeTop(1000);
        //默认传过来的id是-1
        if (id == -1) {
            id=typeList.get(0).getId();//取第一个type
        }
        BlogQuery blogQuery=new BlogQuery();
        blogQuery.setTypeId(id);
        //所有的分类
        model.addAttribute("types",typeList);
        //根据分类查询的博客列表
        model.addAttribute("page",blogService.listBlog(pageable,blogQuery));
        model.addAttribute("activeTypeId",id);
        return "types";
    }
}
