package com.xlydbb.myblog.repository;

import com.xlydbb.myblog.pojo.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BlogRepository extends JpaRepository<Blog,Long>,JpaSpecificationExecutor<Blog>{
}
