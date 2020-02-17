package com.xlydbb.myblog.service;

import com.xlydbb.myblog.exception.NotFoundException;
import com.xlydbb.myblog.pojo.BlogType;
import com.xlydbb.myblog.repository.BlogTypeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    BlogTypeRepository blogTypeRepository;
    @Transactional
    @Override
    public BlogType saveType(BlogType blogType) {
        return blogTypeRepository.save(blogType);
    }

    @Override
    public BlogType getType(Long id) {
        return blogTypeRepository.getOne(id);
    }

    @Override
    public BlogType getTypeByName(String name) {
        return blogTypeRepository.findByName(name);
    }

    @Override
    public Page<BlogType> listType(Pageable pageable) {
        return blogTypeRepository.findAll(pageable);
    }

    @Override
    public List<BlogType> listType() {
        return blogTypeRepository.findAll();
    }

    @Transactional
    @Override
    public BlogType updateType(Long id, BlogType type) {
        BlogType blogType = blogTypeRepository.getOne(id);
        if(blogType == null){
            throw new NotFoundException("不存在该分类");
        }
        BeanUtils.copyProperties(type,blogType);
        return blogTypeRepository.save(blogType);
    }
    @Transactional
    @Override
    public BlogType deleteType(Long id) {
        BlogType blogType = blogTypeRepository.getOne(id);
        if(blogType == null){
            throw new NotFoundException("不存在该分类");
        }
        blogTypeRepository.delete(blogType);
        return blogType;
    }

}
