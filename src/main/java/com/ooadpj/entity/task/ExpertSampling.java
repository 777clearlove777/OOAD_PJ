package com.ooadpj.entity.task;

import com.ooadpj.entity.Grade;
import com.ooadpj.entity.user.Expert;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * @author: 杜东方
 * @date: 2021/1/4
 * @description: 专家抽检
 */
@Data
public class ExpertSampling extends SupervisoryTask {
    /**
     * 专家
     */
    private Expert expert;

    /**
     * 本次监管任务下，专家得分及纪录
     */
    private Grade expertGrade;

    public ExpertSampling(TaskType taskType, Integer id, String name, String description, Date deadline, Expert expert) {
        super(taskType, id, name, description, deadline, new HashMap<Integer, AgriMarketSupervisionTask>(20));
        setTaskType(TaskType.ExpertSampling);
        this.expert = expert;
    }
}
