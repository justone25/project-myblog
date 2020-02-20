package com.xlydbb.myblog.service;

import com.xlydbb.myblog.pojo.BlogType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TypeService {
    BlogType saveType(BlogType blogType);
    BlogType getType(Long id);
    BlogType getTypeByName(String name);
    Page<BlogType> listType(Pageable pageable);
    List<BlogType> listType();
    List<BlogType> listTopType(Integer size);
    BlogType updateType(Long id,BlogType type);
    BlogType deleteType(Long id);
}
