package com.xlydbb.myblog.repository;

import com.xlydbb.myblog.pojo.Blog;
import com.xlydbb.myblog.pojo.BlogTag;
import com.xlydbb.myblog.pojo.BlogType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog,Long>,JpaSpecificationExecutor<Blog>{
    List<Blog> findByRecommendedIsTrue(Pageable pageable);
    Page<Blog> findBlogsByPublishedIsTrue(Pageable pageable);
    Page<Blog> findBlogsByPublishedIsTrueAndBlogTypeIs(BlogType blogType,Pageable pageable);
    Page<Blog> findBlogsByPublishedIsTrueAndBlogTagsContains(BlogTag blogTag,Pageable pageable);
    @Modifying
    @Query("update Blog b set b.views = b.views+1 where b.id=?1")
    int updateViews(Long id);
    @Query("select blog from Blog blog where blog.title like CONCAT('%',?1,'%')  or blog.content like CONCAT('%',?1,'%') and blog.published=true")
    Page<Blog> findByTitleOrContentLikeAndPublishedIsTrue(String query,Pageable pageable);
    @Query("select function('date_format',b.updateTime,'%Y') as year from Blog b group by function('date_format',b.updateTime,'%Y') order by year desc")
    List<String> findGroupYear();
    @Query("select b from Blog  b where function('date_format',b.updateTime,'%Y') =?1 and b.published=true ")
    List<Blog> findByYear(String year);
    Long countAllByPublishedIsTrue();
}
