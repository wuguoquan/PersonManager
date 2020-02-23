package com.wgq.web;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "t_person_url")
@EntityListeners(AuditingEntityListener.class)
public class PersonUrl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String url;
    private String title;
    private String label;

    /**
     * 更新时间
     */
    @LastModifiedDate
    @Column(nullable = false)
    private Long updateTime;

    /**
     * 创建时间
     */
    @Temporal(TemporalType.DATE)
    @CreatedDate
    @Column(updatable = false, nullable = false)
    private Date createTime;

    @Override
    public String toString() {
        return "PersonUrl{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", label='" + label + '\'' +
                ", updateTime=" + updateTime +
                ", createTime=" + createTime +
                '}';
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
