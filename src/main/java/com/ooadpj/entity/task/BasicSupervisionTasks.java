package com.ooadpj.entity.task;

import com.ooadpj.entity.Grade;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: 杜东方/孟超
 * @date: 2021/1/4
 * @description: 农贸市场自检（即原监管任务），关联多个农贸市场监管任务
 */
@Data
public class BasicSupervisionTasks extends SupervisoryTask {

    /**
     *本次市场自检任务下，每个农贸市场的得分情况
     */
    private Map<Integer, Grade> marketGrades;

    public BasicSupervisionTasks(TaskType taskType, Integer id, String name, String description, Date deadline) {
        super(taskType, id, name, description, deadline, new HashMap<Integer, AgriMarketSupervisionTask>(20));
        setTaskType(TaskType.BasicSupervision);
        marketGrades = new HashMap<>(20);
    }
}
