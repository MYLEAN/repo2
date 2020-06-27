package com.zq.blog.service;

import com.zq.blog.po.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> listCommentByBlogId(Long blogId);//根据博客id获取评论列表

    //提交数据时保存comment
    Comment saveComment(Comment comment);
}
