package com.jun.seeyouagain.common.model.jpa;

import lombok.Data;

import javax.naming.Name;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "t_user_info")
@Data
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_account")
    private String userAccount;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "task_user",
            joinColumns = {@JoinColumn(name = "user_info_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "task_id", referencedColumnName = "id")}
    )
    private List<TaskInfo> tasks;
}
