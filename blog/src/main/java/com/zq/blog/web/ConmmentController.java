package com.zq.blog.web;

import com.zq.blog.po.Blog;
import com.zq.blog.po.Comment;
import com.zq.blog.po.User;
import com.zq.blog.service.BlogService;
import com.zq.blog.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
public class ConmmentController {

    @Resource
    private CommentService commentService;

    @Resource
    private BlogService blogService;

    @GetMapping("/conmments/{blogId}")//根据博客id获取评论列表
    public String conmments(@PathVariable Long blogId, Model model){
        model.addAttribute("conmments",commentService.listCommentByBlogId(blogId));
        return "blog :: conmmentList";
    }

    @PostMapping("/conmments")
    public String post(Comment comment, HttpSession session){//发布评论之后把评论(comment)接收过来,接收的commnet中只有blog的id
        Long blogId=comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));
        User user= (User) session.getAttribute("user");
        if (user != null) {
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        }
//        comment.setAvatar("https://unsplash.it/100/100?image=1005");
        commentService.saveComment(comment);
        return "redirect:/conmments/"+blogId;
    }
}
