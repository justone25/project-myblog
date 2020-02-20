package com.xlydbb.myblog.repository;

import com.xlydbb.myblog.pojo.BlogTag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BlogTagRepository extends JpaRepository<BlogTag,Long> {
    BlogTag findByName(String name);
    @Query("select t from BlogTag t")
    List<BlogTag> findTop(Pageable pageable);
}
