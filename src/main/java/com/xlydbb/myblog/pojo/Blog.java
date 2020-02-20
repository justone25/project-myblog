package com.xlydbb.myblog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_blog")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String content;
    private String description;
    private String firstPicture;
    //原创，转载，翻译
    private String flag;
    @Transient
    private String tagIds;
    private Integer views;
    //赞赏是否开启
    private boolean appreciation;
    //转载声明是否开启
    private boolean shareStatement;
    //评论是否开启
    private boolean commentabled;
    //是否发布
    private boolean published;
    //是否推荐
    private boolean recommended;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
    //many端作关系维护方
    @ManyToOne
    private BlogType blogType;
    //关系维护端
    //级联新增，新增一个blog的时候，blogtag也新增
    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<BlogTag> blogTags = new ArrayList<>();
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "blog")
    private List<Comment> commentList = new ArrayList<>();
    public void init(){
        this.tagIds = tagsToIds(this.blogTags);
    }
    private String tagsToIds(List<BlogTag> tags){
        if(!tags.isEmpty()){
            StringBuffer ids = new StringBuffer();
            boolean flag = false;
            for (BlogTag tag:
                 tags) {
                if(flag){
                    ids.append(",");
                }else{
                    flag = true;
                }
                ids.append(tag.getId());
            }
            return ids.toString();
        }else{
            return tagIds;
        }
    }
}
