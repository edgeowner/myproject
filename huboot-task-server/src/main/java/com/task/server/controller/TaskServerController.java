package com.task.server.controller;

import com.task.server.dto.*;
import com.task.server.service.TaskFeedbackService;
import com.task.server.service.TaskRegisterService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Administrator on 2018/8/19 0019.
 */
@RestController
public class TaskServerController {

    @Autowired
    private TaskRegisterService taskRegisterService;
    @Autowired
    private TaskFeedbackService feedbackService;

    @PostMapping("/task_server/register_secheduled_task")
    @ApiOperation(response = void.class, value = "注册定时任务")
    public void registerSecheduledTask(@RequestBody SecheduledRegisterDTO registerDTO) {
        taskRegisterService.registerSecheduledTask(registerDTO);
    }

    @PostMapping("/task_server/register_delay_task")
    @ApiOperation(response = void.class, value = "注册延迟任务")
    public DelayRegisterResultDTO registerDelayTask(@RequestBody DelayRegisterDTO registerDTO) {
        return taskRegisterService.registerDelayTask(registerDTO);
    }

    @PostMapping("/task_server/cancel_delay_task")
    @ApiOperation(response = void.class, value = "取消延迟任务")
    public void cancelDelayTask(@RequestBody List<String> taskIdList) {
        taskRegisterService.cancelDelayTask(taskIdList);
    }

    @PostMapping("/task_server/cancel_delay_task/with_parameters")
    @ApiOperation(response = void.class, value = "取消延迟任务")
    public void cancelDelayTask(@RequestBody DelayRequestDTO requestDTO) {
        taskRegisterService.cancelDelayTask(requestDTO);
    }

    @PostMapping("/task_server/task_feedback/{logId}")
    @ApiOperation(response = void.class, value = "定时任务执行结果反馈")
    public void taskFeedback(@PathVariable("logId")String logId, @RequestBody TaskResultDTO resultDTO) {
        feedbackService.taskFeedback(logId, resultDTO);
    }
}
