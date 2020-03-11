package com.xlydbb.myblog.service;

import com.xlydbb.myblog.pojo.Blog;
import com.xlydbb.myblog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BlogService {
    Blog getBlog(Long id);
    Blog getAndConvent(Long id);
    Page<Blog> listBlog(Pageable pageable,BlogQuery blog);
    Page<Blog> listBlog(Pageable pageable,String query);
    Page<Blog> listPublishedBlog(Pageable pageable);
    Page<Blog> listPublishedAndTypeBlog(Pageable pageable,Long typeId);
    Page<Blog> listPublishedAndTagBlog(Pageable pageable,Long tagId);
    List<Blog> listRecommenBlogTop(Integer size);
    Blog saveBlog(Blog blog);
    Blog updateBlog(Long id,Blog blog);
    Blog deleteBlog(Long id);
    Map<String,List<Blog>> listByYear();
    Long countBlog();
}
