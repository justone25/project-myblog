package com.xlydbb.myblog.service;

import com.xlydbb.myblog.pojo.BlogTag;
import com.xlydbb.myblog.pojo.BlogType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TagService {
    BlogTag saveTag(BlogTag bLogTag);
    BlogTag getTag(Long id);
    BlogTag getTagByName(String name);
    Page<BlogTag> listTag(Pageable pageable);
    BlogTag updateTag(Long id,BlogTag blogTag);
    //返回删除对象
    BlogTag deleteTag(Long id);
}
