package com.xlydbb.myblog.service;

import com.xlydbb.myblog.pojo.Comment;
import com.xlydbb.myblog.repository.CommentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentRepository commentRepository;
    //存放迭代找出的所有子代合集
    private List<Comment> tempReplys = new ArrayList<>();
    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        Sort sort = Sort.by(Sort.Direction.DESC,"createTime");
        List<Comment> comments = commentRepository.findByBlogIdAndParentCommentIsNull(blogId,sort);
        return combineChildren(comments);
    }
    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();
        if(parentCommentId != -1){
            comment.setParentComment(commentRepository.getOne(parentCommentId));
        }else{
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }

    /**
     *
     * @param comments 顶级节点，即博客下的一级评论
     * @return
     */
    private List<Comment> combineChildren(List<Comment> comments){
        List<Comment> purposeComments = new ArrayList<>();
        for (Comment comment : comments) {
            Comment newComment = new Comment();
            BeanUtils.copyProperties(comment,newComment);
            List<Comment> replys = newComment.getReplyComments();
            for (Comment reply : replys) {
                recursively(reply);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            newComment.setReplyComments(tempReplys);
            purposeComments.add(newComment);
            //清空临时缓存存放区
            tempReplys= new ArrayList<>();
        }
        return purposeComments;
    }
    /**
     * 存放迭代找出的所有子代的集合
     * @param comment
     */
    private void recursively(Comment comment){
        //二级评论添加到临时存放集合
        tempReplys.add(comment);
        if(comment.getReplyComments().size()>0){
            List<Comment> replys = comment.getReplyComments();
            for (Comment reply : replys) {
                recursively(reply);
            }
        }
    }
}
