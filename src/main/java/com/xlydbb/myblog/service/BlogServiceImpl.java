package com.xlydbb.myblog.service;

import com.xlydbb.myblog.exception.NotFoundException;
import com.xlydbb.myblog.pojo.Blog;
import com.xlydbb.myblog.pojo.BlogType;
import com.xlydbb.myblog.repository.BlogRepository;
import com.xlydbb.myblog.vo.BlogQuery;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    BlogRepository blogRepository;
    @Override
    public Blog getBlog(Long id) {
        return blogRepository.getOne(id);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();
                if(!"".equals(blog.getTitle())&& blog.getTitle()!=null){
                    predicateList.add(criteriaBuilder.like(root.<String>get("title"),"%"+blog.getTitle()+"%"));
                }
                if(blog.getTypeId()!=null){
                    predicateList.add(criteriaBuilder.equal(root.<BlogType>get("blogType").
                            get("id"),blog.getTypeId()));
                }
                if(blog.isRecommend()){
                    predicateList.add(criteriaBuilder.equal(root.<Boolean>get("recommended"),blog.isRecommend()));
                }
                criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));
                return null;
            }
        },pageable);
    }

    @Override
    public Blog saveBlog(Blog blog) {
        return blogRepository.save(blog);
    }

    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog blogDB = blogRepository.getOne(id);
        if(blogDB == null){
            throw new NotFoundException("该博客不存在");
        }
        BeanUtils.copyProperties(blog,blogDB);
        return blogRepository.save(blogDB);
    }

    @Override
    public Blog deleteBlog(Long id) {
        Blog blogDB = blogRepository.getOne(id);
        if(blogDB == null){
            throw new NotFoundException("该博客不存在");
        }
        blogRepository.delete(blogDB);
        return blogDB;
    }
}
