package com.zq.blog.web.admin;

import com.zq.blog.po.Blog;
import com.zq.blog.po.Tag;
import com.zq.blog.po.User;
import com.zq.blog.service.BlogService;
import com.zq.blog.service.TagService;
import com.zq.blog.service.TypeService;
import com.zq.blog.vo.BlogQuery;
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

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String INPUT="admin/blogs-input";
    private static final String LIST="admin/blogs";
    private static final String REDIRECT_LIST="redirect:/admin/blogs";

    @Resource
    private BlogService blogService;

    @Resource
    private TypeService typeService;

    @Resource
    private TagService tagService;

    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 2,sort = {"createTime"},direction = Sort.Direction.ASC)
                                    Pageable pageable, BlogQuery blog, Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return LIST;
    }

    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 2,sort = {"createTime"},direction = Sort.Direction.ASC)
                                Pageable pageable, BlogQuery blog, Model model){
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return "admin/blogs :: blogList";
    }

    @GetMapping("/blogs/input")
    public String addblogs(Model model){
        model.addAttribute("blog",new Blog());
        model.addAttribute("tags",tagService.listTag());
        model.addAttribute("types",typeService.listType());
        return INPUT;
    }

    @GetMapping("/blogs/{id}/input")
    public String edit(Model model, @PathVariable Long id){
        model.addAttribute("tags",tagService.listTag());
        model.addAttribute("types",typeService.listType());
        Blog blog=blogService.getBlog(id);
        blog.init();//初始化tagIds得到1,2这样的字符串
        model.addAttribute("blog",blog);
        return INPUT;
    }

    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        blogService.delete(id);
        attributes.addFlashAttribute("message","删除成功");
        return REDIRECT_LIST;
    }

    @PostMapping("/blogs")//提交新增博客
    public String addpost(Blog blog, RedirectAttributes attributes, HttpSession session){
        User user= (User) session.getAttribute("user");
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTags(blog.getTagIds()));
        Blog b;
        if (blog.getId() != null) {
            b=blogService.update(blog.getId(),blog);
        }else {
            b=blogService.save(blog);
        }
        if (b == null) {
            attributes.addFlashAttribute("message","发布失败");
        }else {
            attributes.addFlashAttribute("message","发布成功");
        }
        return REDIRECT_LIST;
    }
}
