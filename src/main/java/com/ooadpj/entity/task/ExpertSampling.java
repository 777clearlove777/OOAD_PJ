package com.ooadpj.entity.task;

import com.ooadpj.common.DeductionStandard;
import com.ooadpj.entity.Grade;
import com.ooadpj.entity.SamplingReport;
import com.ooadpj.entity.user.Expert;
import lombok.Data;

import java.util.*;

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

    public ExpertSampling(TaskType taskType, String id, String name, String description, Date deadline, Expert expert) {
        super(taskType, id, name, description, deadline, new HashMap<Integer, AgriMarketSupervisionTask>(20));
        setTaskType(TaskType.ExpertSampling);
        this.expert = expert;
    }

    @Override
    public void taskAssess() {
        Map<Integer,AgriMarketSupervisionTask> taskMap = getMarketSupervisionTasks();
        Set<Integer> taskKey = taskMap.keySet();

        //开始迭代key集合
        Iterator<Integer> it = taskKey.iterator();
        while (it.hasNext()) {
            Integer key = it.next();
            AgriMarketSupervisionTask agriMarketSupervisionTask = taskMap.get(key);

            //获取抽检项报告
            ArrayList<SamplingReport> samplingReportArrayList = agriMarketSupervisionTask.getSamplingReportArrayList();
            for (int i = 0; i<samplingReportArrayList.size();i++){
                SamplingReport samplingReport = samplingReportArrayList.get(i);
                //获取录入结果日期
                Date resultDate = samplingReport.getRecordDate();

                //该类别按时完成
                if(resultDate.before(getDeadline())){
                    expertGrade.getGradeRecord().add(samplingReport.getRecordProductType()+DeductionStandard.ADDSCORE);
                    expertGrade.setGrade(expertGrade.getGrade()+10);
                    continue;
                }

                //未按时完成
                if(resultDate.after(getDeadline())){
                    expertGrade.getGradeRecord().add(samplingReport.getRecordProductType()+DeductionStandard.PEEKSCORE);
                    expertGrade.setGrade(expertGrade.getGrade()-10);
                }

                //20以上未完成
                if((resultDate.getTime() - getDeadline().getTime()) >= 86400000 * 20){
                    expertGrade.getGradeRecord().add(samplingReport.getRecordProductType()+DeductionStandard.OVERTIMESCORE);
                    expertGrade.setGrade(expertGrade.getGrade()-20);
                }

            }
        }
    }

    @Override
    public void print() {

    }
}
