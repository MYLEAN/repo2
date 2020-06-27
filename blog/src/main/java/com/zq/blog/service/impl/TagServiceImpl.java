package com.zq.blog.service.impl;

import com.zq.blog.NotFoundException;
import com.zq.blog.dao.TagRepository;
import com.zq.blog.po.Tag;
import com.zq.blog.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Resource
    private TagRepository tagRepository;

    @Override
    public Page<Tag> list(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public void delete(Long id) {
        tagRepository.deleteById(id);
    }

    @Override
    public List<Tag> listTags(String ids) {

        return tagRepository.findAllById(convertToList(ids));
    }

    private List<Long> convertToList(String ids){
        List<Long> list=new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idarry=ids.split(",");
            for (int i=0;i<idarry.length;i++){
                list.add(new Long(idarry[i]));
            }
        }
        return list;
    }

    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> listTagTop(Integer size) {
        Sort sort=Sort.by(Sort.Direction.DESC,"blogs.size");
        Pageable pageable= PageRequest.of(0,size,sort);
        return tagRepository.findTop(pageable);
    }

    @Override
    public Tag update(Long id, Tag tag) {
        Tag t=tagRepository.getOne(id);
        if(t==null){
            throw new NotFoundException("这个标签不存在");
        }
        BeanUtils.copyProperties(tag,t);
        tagRepository.save(t);
        return t;
    }

    @Override
    public Tag getTag(Long id) {
        return tagRepository.getOne(id);
    }

    @Override
    public Tag getByname(String name) {
        return tagRepository.getByname(name);
    }
}
