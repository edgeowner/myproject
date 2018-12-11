package com.huboot.business.common.plug.task.job_pool;

import com.huboot.business.common.component.exception.BizException;
import com.huboot.business.common.plug.task.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

//@Component
public class DelayJobPool implements JobPool {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisTemplate template;

    public static final String JOB_POOL_DELAY_KEY= "task_job_pool_delay";

    @Override
    public String push(Job job) {
        if(template.opsForHash().hasKey(JOB_POOL_DELAY_KEY,job.getId())) {
            logger.error("任务已经存在,请不要重复添加jobId:{}",job.getId());
            throw new BizException("任务已经存在,请不要重复添加");
        }
        template.opsForHash().put(JOB_POOL_DELAY_KEY,job.getId(),job);
        return job.getId();
    }

    @Override
    public void updJob(Job job) {
        if(template.opsForHash().hasKey(JOB_POOL_DELAY_KEY,job.getId())) {
            logger.warn("任务已存在,覆盖任务jobId:{}",job.getId());
            template.opsForHash().delete(JOB_POOL_DELAY_KEY,job.getId());
        }
        push(job);
    }


    @Override
    public Boolean del(String jobId) {
        if(template.opsForHash().hasKey(JOB_POOL_DELAY_KEY,jobId)) {
            logger.warn("任务已存在,jobId:{}",jobId);
            template.opsForHash().delete(JOB_POOL_DELAY_KEY,jobId);
            return true;
        }
        return true;
    }

    @Override
    public Boolean hasJob(String jobId) {
        return template.opsForHash().hasKey(JOB_POOL_DELAY_KEY,jobId);
    }
}