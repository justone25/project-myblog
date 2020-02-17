package com.xlydbb.myblog.service;

import com.xlydbb.myblog.exception.NotFoundException;
import com.xlydbb.myblog.pojo.BlogTag;
import com.xlydbb.myblog.repository.BlogTagRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    BlogTagRepository tagRepository;
    @Transactional
    @Override
    public BlogTag saveTag(BlogTag bLogTag) {
        return tagRepository.save(bLogTag);
    }

    @Override
    public BlogTag getTag(Long id) {
        return tagRepository.getOne(id);
    }

    @Override
    public BlogTag getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    @Override
    public Page<BlogTag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public BlogTag updateTag(Long id, BlogTag blogTag) {
        BlogTag tag = tagRepository.getOne(id);
        if(tag == null){
            throw new NotFoundException("不存在该标签");
        }
        BeanUtils.copyProperties(blogTag,tag);
        return tagRepository.save(tag);
    }

    @Override
    public BlogTag deleteTag(Long id) {
        BlogTag tag = tagRepository.getOne(id);
        if(tag == null){
            throw new NotFoundException("不存在该标签");
        }
        tagRepository.delete(tag);
        return tag;
    }
}
