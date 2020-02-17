package com.xlydbb.myblog.repository;

import com.xlydbb.myblog.pojo.BlogTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogTagRepository extends JpaRepository<BlogTag,Long> {
    BlogTag findByName(String name);
}
