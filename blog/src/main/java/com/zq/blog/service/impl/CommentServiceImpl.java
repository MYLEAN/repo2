package com.zq.blog.service.impl;

import com.zq.blog.dao.CommentRepository;
import com.zq.blog.po.Comment;
import com.zq.blog.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentRepository commentRepository;
    private List<Comment> tempReplys=new ArrayList<>();

    @Transactional
    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        List<Comment> comments=commentRepository.findByBlogIdAndParentCommentNull(blogId);
        return eachComment(comments);
    }

    private List<Comment> eachComment(List<Comment> comments) {
        List<Comment> commentsView=new ArrayList<>();
        for(Comment comment:comments){
            Comment c=new Comment();
            BeanUtils.copyProperties(comment,c);
            commentsView.add(c);
        }
        //合并评论的各层子代到第一级子代集合中
        combineChildren(commentsView);
        return commentsView;
    }

    private void combineChildren(List<Comment> comments) {
        for(Comment comment:comments){
            List<Comment> replys1=comment.getReplayComment();
            for(Comment reply1:replys1){
                //循环迭代，找出子代，存放在tempReplys中
                recursively(reply1);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplayComment(tempReplys);
            //清除临时存放区
            tempReplys=new ArrayList<>();
        }
    }

    private void recursively(Comment comment) {
        tempReplys.add(comment);//顶级节点添加到临时存放集合
        if(comment.getReplayComment().size()>0){
            List<Comment> replys=comment.getReplayComment();
            for(Comment reply:replys){
                tempReplys.add(reply);
                if(reply.getReplayComment().size()>0){
                    recursively(reply);
                }
            }
        }
    }

    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentId=comment.getParentComment().getId();//默认为-1
        if (parentCommentId != -1) {
            comment.setParentComment(commentRepository.getOne(parentCommentId));
        }else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }
}
