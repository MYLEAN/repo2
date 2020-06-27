package com.zq.blog.service;

import com.zq.blog.po.Blog;
import com.zq.blog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BlogService {
    Blog save(Blog blog);

    Blog getBlog(Long id);

    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    Page<Blog> listBlog(Pageable pageable);

    Blog getAndconvert(Long id);

    Integer updateView(Long id);

    Page<Blog> listBlog(Long tagId,Pageable pageable);

    Page<Blog> listBlog(String query,Pageable pageable);

    List<Blog> listReconmmendBlogTop(Integer size);

    Map<String ,List<Blog>> archiveBlog();

    Blog update(Long id,Blog blog);

    Long countBlog();

    void delete(Long id);

}
