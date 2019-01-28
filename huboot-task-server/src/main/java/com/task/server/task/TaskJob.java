package com.task.server.task;

import com.task.server.entity.SecheduledTaskInfo;
import com.task.server.entity.TaskExecuteLog;
import com.task.server.service.DelayTaskSupport;
import com.task.server.service.TaskExecuteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Administrator on 2018/8/20 0020.
 */
@Component
public class TaskJob {

    @Autowired
    private TaskExecuteService executeService;
    @Autowired
    private DelayTaskSupport delayTaskSupport;
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 执行定时任务
     */
    @Scheduled(fixedDelay = 1000)
    public void executeSecheduledTask() {
        executeService.executeSecheduledTask();
    }

    /**
     * 执行延迟任务
     */
    @Scheduled(fixedDelay = 1000)
    public void executeDelayTask() {
        executeService.executeDelayTask();
    }

    /**
     * 延迟任务归档
     * 每半小时跑一次
     */
    @Scheduled(cron = "0 0/30 * * * ? ")
    public void delayTaskArchive() {
        delayTaskSupport.archive();
    }

    /**
     * 延迟任务反馈超时
     * 每15分钟跑一次
     */
    @Scheduled(cron = "0 0/15 * * * ? ")
    public void delayTaskFeedbackTimeout() {
        delayTaskSupport.feedbackTimeout();
    }


    /**
     * 删除15天前日志
     */
    @Scheduled(cron = "0 0/10 * * * ? ")
    public void clearLog() {
        Calendar ca = new GregorianCalendar();
        ca.setTime(new Date());
        ca.add(Calendar.DAY_OF_YEAR, -15);
        Date date = ca.getTime();
        Query query = Query.query(Criteria.where("beginTime").lte(date));
        mongoTemplate.remove(query, TaskExecuteLog.class);
    }

}
