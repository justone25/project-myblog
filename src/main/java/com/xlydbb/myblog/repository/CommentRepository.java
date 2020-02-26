package com.xlydbb.myblog.repository;

import com.xlydbb.myblog.pojo.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByBlogIdAndParentCommentIsNull(Long blogId, Sort sort);
}
