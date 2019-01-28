package com.task.server.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Administrator on 2018/8/19 0019.
 */
@Data
@Document(collection = "delay_task_info")
public class DelayTaskInfo extends DelayTaskParent {

    @Id
    private String id;
}
