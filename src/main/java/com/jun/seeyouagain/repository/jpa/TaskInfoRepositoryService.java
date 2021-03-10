package com.jun.seeyouagain.repository.jpa;

import com.jun.seeyouagain.common.model.jpa.TaskInfo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

public interface TaskInfoRepositoryService extends Repository<TaskInfo, String> {
    //可以省略select Book
    @Query(value = "select b from TaskInfo b where b.taskName=?1")
    TaskInfo queryByName(String taskName);

    //采用原生态的SQL语句
    @Query(value = "select * from t_task_info where task_name=?", nativeQuery = true)
    TaskInfo queryByName2(String taskName);

    @Modifying
    @Query("update TaskInfo b set b.taskCode=?2 where b.taskName=?1")
    void updateNameAndCode1(String name, String code);

    //采用参数占位
    @Modifying
    @Query("update TaskInfo b set b.taskCode=:code where b.taskName=:name")
    void updateNameAndCode2(@Param("name") String name, @Param("code") String code);
}
