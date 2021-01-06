package com.ooadpj.service.supervisionBureauService;

import com.ooadpj.entity.task.SupervisoryTask;

/**
 * @author: 孟超
 * @date: 2021/1/6
 * @description: 监管局查看监管任务
 */
public class CheckTasks {

    private PublishTasks publishTasks;
    private SupervisoryTask supervisoryTask;

    public void printTask(){
        //获取监管任务
        publishTasks = new PublishTasks();
        supervisoryTask = publishTasks.getBasicSupervisionTasks();
        supervisoryTask.print();
    }
}
