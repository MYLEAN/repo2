package com.zq.blog.service;

import com.zq.blog.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {
    Tag save(Tag tag);

    void delete(Long id);

    Tag update(Long id,Tag tag);

    Tag getTag(Long id);

    Tag getByname(String name);

    Page<Tag> list(Pageable pageable);

    List<Tag> listTag();

    List<Tag> listTagTop(Integer size);

    List<Tag> listTags(String ids);
}
