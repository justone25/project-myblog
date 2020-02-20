package com.xlydbb.myblog.service;

import com.xlydbb.myblog.exception.NotFoundException;
import com.xlydbb.myblog.pojo.BlogTag;
import com.xlydbb.myblog.repository.BlogTagRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

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
    public List<BlogTag> listTag() {
        return tagRepository.findAll();
    }

    @Override
    public List<BlogTag> listTag(String ids) {
        return tagRepository.findAllById(converToList(ids));
    }

    @Override
    public List<BlogTag> listTopTag(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"blogList.size");
        Pageable pageable = PageRequest.of(0,size,sort);
        return tagRepository.findTop(pageable);
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
    private  List<Long> converToList(String ids){
        List<Long> list = new ArrayList<>();
        if(!StringUtils.isEmpty(ids)){
            String [] idArray = ids.split(",");
            for(int i =0;i<idArray.length;i++){
                Long tagId;
                try {
                    tagId = Long.valueOf(idArray[i]);
                } catch (NumberFormatException e) {
                   //说明是在页面上新增的标签
                    BlogTag blogTag = new BlogTag();
                    blogTag.setName(idArray[i]);
                    BlogTag blogTagDB = tagRepository.save(blogTag);
                    tagId = blogTagDB.getId();
                }
                list.add(tagId);
            }
        }
        return list;
    }
}
