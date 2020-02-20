package com.xlydbb.myblog.service;

import com.xlydbb.myblog.pojo.Blog;
import com.xlydbb.myblog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BlogService {
    Blog getBlog(Long id);
    Blog getAndConvent(Long id);
    Page<Blog> listBlog(Pageable pageable,BlogQuery blog);
    Page<Blog> listBlog(Pageable pageable,String query);
    Page<Blog> listPublishedBlog(Pageable pageable);
    List<Blog> listRecommenBlogTop(Integer size);
    Blog saveBlog(Blog blog);
    Blog updateBlog(Long id,Blog blog);
    Blog deleteBlog(Long id);
}
