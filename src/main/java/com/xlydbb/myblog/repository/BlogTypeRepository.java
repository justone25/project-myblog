package com.xlydbb.myblog.repository;

import com.xlydbb.myblog.pojo.BlogType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogTypeRepository extends JpaRepository<BlogType,Long> {
    BlogType findByName(String name);
}
