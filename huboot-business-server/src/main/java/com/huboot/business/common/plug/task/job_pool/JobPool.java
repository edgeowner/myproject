package com.huboot.business.common.plug.task.job_pool;

import com.huboot.business.common.plug.task.Job;

public interface JobPool {

    /**
     * 添加一个job
     *
     * @return jobId
     **/
    String push(Job job);

    /***
     * 修改job
     * @return boolean
     * **/
    void updJob(Job job);

    /**
     * 删除一个job
     *
     * @return boolean
     **/
    Boolean del(String jobId);

    /**
     * 判断是否包含一个job
     *
     * @return boolean
     **/
    Boolean hasJob(String jobId);


}
