package com.huboot.business.common.plug.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.math.BigDecimal;

//@Component
public class DelayBucket {

    @Autowired
    private RedisTemplate template;

    public static final String JOB_BUCKET_DELAY_PIX= "task_job_bucket_delay_";

    public void add(Job job){
        Double score = new BigDecimal(job.getCreateTimeStamp()).add(new BigDecimal(job.getDelay())).doubleValue();
        template.opsForZSet().add(JOB_BUCKET_DELAY_PIX+job.getTopic(),job.getId(),score);
    }
}
