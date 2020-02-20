package com.xlydbb.myblog.repository;

import com.xlydbb.myblog.pojo.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog,Long>,JpaSpecificationExecutor<Blog>{
    List<Blog> findByRecommendedIsTrue(Pageable pageable);
    Page<Blog> findBlogsByPublishedIsTrue(Pageable pageable);
    @Query("select blog from Blog blog where blog.title like CONCAT('%',?1,'%')  or blog.content like CONCAT('%',?1,'%') and blog.published=true")
    Page<Blog> findByTitleOrContentLikeAndPublishedIsTrue(String query,Pageable pageable);
}
