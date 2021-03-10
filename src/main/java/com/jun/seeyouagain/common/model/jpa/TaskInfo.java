package com.jun.seeyouagain.common.model.jpa;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * Entity注解标明实体
 * Table标注标明，默认会采用类名
 */
@Entity
@Table(name = "t_task_info")
@Data
public class TaskInfo {
    /**
     * 主键id
     * 依赖数据库的自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    /**
     * 采用lombok标注控制
     */
    @NotNull
    @Size(min = 1, message = "任务名不能为空")
    @Column(name = "task_name")
    private String taskName;

    @Column(name = "task_code")
    private String taskCode;

    @Column(name = "create_time")
    private Date createTime;

    /**
     * 多对多关系,targetEntity是对象的实体
     */
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @Size(min = 1, message = "请至少填写一个参与人")
    @JoinTable(name = "task_user",
            joinColumns = {@JoinColumn(name = "user_info_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "task_id", referencedColumnName = "id")}
    )
    private List<UserInfo> users;

    /**
     * 持久化之前的操作
     */
    @PrePersist
    void createData() {
        this.createTime = new Date();
    }
}
