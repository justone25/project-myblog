package com.xlydbb.myblog.repository;

import com.xlydbb.myblog.pojo.BlogType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BlogTypeRepository extends JpaRepository<BlogType,Long> {
    BlogType findByName(String name);
    @Query("select t from BlogType t ")
    List<BlogType> findTop(Pageable pageable);
}
