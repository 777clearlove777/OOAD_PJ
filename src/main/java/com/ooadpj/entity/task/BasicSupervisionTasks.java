package com.ooadpj.entity.task;

import com.ooadpj.common.DeductionStandard;
import com.ooadpj.entity.Grade;
import com.ooadpj.entity.SamplingReport;
import com.ooadpj.entity.product.AgriculturalProduct;
import com.ooadpj.entity.product.AquaticProducts;
import com.ooadpj.entity.product.ProductType;
import lombok.Data;

import java.util.*;

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

    public BasicSupervisionTasks(TaskType taskType, String id, String name, String description, Date deadline) {
        super(taskType, id, name, description, deadline, new HashMap<Integer, AgriMarketSupervisionTask>(20));
        setTaskType(TaskType.BasicSupervision);
        marketGrades = new HashMap<>(20);
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

                Grade marketGrade = marketGrades.get(i);

                //该类别按时完成
                if(resultDate.before(getDeadline())){
                    marketGrade.getGradeRecord().add(samplingReport.getRecordProductType()+ DeductionStandard.ADDSCORE);
                    marketGrade.setGrade(marketGrade.getGrade()+10);
                    continue;
                }

                //未按时完成
                if(resultDate.after(getDeadline())){
                    marketGrade.getGradeRecord().add(samplingReport.getRecordProductType()+DeductionStandard.PEEKSCORE);
                    marketGrade.setGrade(marketGrade.getGrade()-10);
                }

                //20天以上未完成
                if((resultDate.getTime() - getDeadline().getTime()) >= 86400000 * 20){
                    marketGrade.getGradeRecord().add(samplingReport.getRecordProductType()+DeductionStandard.OVERTIMESCORE);
                    marketGrade.setGrade(marketGrade.getGrade()-20);
                }
            }
        }
    }

    @Override
    public void print() {

        System.out.println("任务类型："+ getTaskType());
        System.out.println("任务id："+ getId());
        System.out.println("任务名称："+getName());
        System.out.println("任务描述：" + getDescription());
        System.out.println("任务截止日期：" + getDeadline());

        Map<Integer, AgriMarketSupervisionTask> agriMarketSupervisionTaskMap = getMarketSupervisionTasks();

        Set<Integer> keySet = agriMarketSupervisionTaskMap.keySet();
        //开始迭代key集合
        Iterator<Integer> it = keySet.iterator();
        System.out.println("农贸市场具体情况：");
        while (it.hasNext()) {
            Integer marketKey = it.next();
            AgriMarketSupervisionTask agriMarketSupervisionTask = agriMarketSupervisionTaskMap.get(marketKey);
            if(!agriMarketSupervisionTask.isFinished){
                System.out.println(agriMarketSupervisionTask.getAgriculturalMarket().getName()+"市场未完成检测的农产品类别");
                Set<AgriculturalProduct> unFinishedProducts = agriMarketSupervisionTask.getUnFinishedProducts();

                for (AgriculturalProduct agriculturalProduct : unFinishedProducts){
                    System.out.println(agriculturalProduct.getId()+"---"+agriculturalProduct.getName());
                }
            }else {
                System.out.println("任务已完成");
            }

            System.out.println("抽检项报告：");
            ArrayList<SamplingReport> samplingReportArrayList = agriMarketSupervisionTask.getSamplingReportArrayList();
            for(SamplingReport samplingReport : samplingReportArrayList){
                System.out.println("抽检结果录入日期：" + samplingReport.getRecordDate());
                System.out.println("抽检类别：" + samplingReport.getRecordProductType());
                System.out.println("抽检不合格数量：" + samplingReport.getUnqualifiedNum());
            }

        }
    }
}
