package com.huboot.business.common.plug.task;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 说明:job
 * @date 2018-03-31
 * @author Mr.zhang
 * **/
@Data
public class Job implements Serializable{

    private static final long serialVersionUID = 1L;

    private String topic;

    private String id = UUID.randomUUID().toString().replace("-","");//Job的唯一标识。用来检索和删除指定的Job信息。

    private Long delay;//Job需要延迟的时间。单位：秒。（服务端会将其转换为绝对时间）

    private Long ttr;//（time-to-run)：Job执行超时时间。单位：秒。

    private String body;//Job的内容，供消费者做具体的业务处理，以json格式存储。

    private LocalDateTime createTime =  LocalDateTime.now();//创建时间

    private LocalDateTime updateTime;//修改时间

    private Long createTimeStamp=System.currentTimeMillis();

    private JobStatus status;

    public static Long TTR_DEFAULT = 10L ;

    public Job(String topic,String jobBody,Long delay){
        this.topic = topic;
        this.delay = delay;
        this.ttr = TTR_DEFAULT;
        this.body = jobBody;
        this.status = JobStatus.ready;
    }

    public Job(String topic,String jobBody,Long delay,Long ttr){
        this.topic = topic;
        this.delay = delay;
        this.ttr = ttr;
        this.body = jobBody;
        this.status = JobStatus.ready;
    }

    public enum JobStatus{
        ready,//可执行状态，等待消费。
        delay,//不可执行状态，等待时钟周期。
        reserved,//已被消费者读取，但还未得到消费者的响应（delete、finish）。
        deleted;//已被消费完成或者已被删除
    }


}
