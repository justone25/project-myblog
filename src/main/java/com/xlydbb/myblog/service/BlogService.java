package com.xlydbb.myblog.service;

import com.xlydbb.myblog.pojo.Blog;
import com.xlydbb.myblog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogService {
    Blog getBlog(Long id);
    Page<Blog> listBlog(Pageable pageable,BlogQuery blog);
    Blog saveBlog(Blog blog);
    Blog updateBlog(Long id,Blog blog);
    Blog deleteBlog(Long id);
}
