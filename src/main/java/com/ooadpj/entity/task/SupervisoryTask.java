package com.ooadpj.entity.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

/**
 * @author: 杜东方/孟超
 * @date: 2021/1/4
 * @description: 监管任务
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public abstract class SupervisoryTask {
    /**
     * 任务类别
     */
    private TaskType taskType;
    /**
     *任务ID
     */
    private String id;
    /**
     * 任务名称
     */
    private String name;
    /**
     * 任务描述
     */
    private String description;
    /**
     * 截止日期
     */
    private Date deadline;
    /**
     * 关联多个农贸市场监管任务(String id, AgriMarketSupervisionTask 农贸市场监管任务)
     */
    private Map<Integer, AgriMarketSupervisionTask> marketSupervisionTasks;
    /**
     * 进行任务评估
     */
    public abstract void taskAssess();
    /**
     * 打印任务信息
     */
    public abstract void print();
}
