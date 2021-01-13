package com.ooadpj.service.commonService;

import com.ooadpj.entity.SamplingReport;
import com.ooadpj.entity.product.AgriculturalProduct;
import com.ooadpj.entity.task.AgriMarketSupervisionTask;
import com.ooadpj.entity.task.BasicSupervisionTasks;
import com.ooadpj.entity.task.ExpertSampling;
import com.ooadpj.entity.task.SupervisoryTask;
import com.ooadpj.service.supervisionBureauService.PublishTasks;

import java.util.*;

/**
 * @author: 孟超
 * @date: 2021/1/6
 * @description: 监管局查看监管任务
 */
public class CheckTasks {

    private PublishTasks publishTasks = new PublishTasks();

    public void printTask(){
        ArrayList<BasicSupervisionTasks> marketSupervisoryTasks = publishTasks.getMarketSupervisoryTasks();
        ArrayList<ExpertSampling> expertSupervisoryTasks = publishTasks.getExpertSupervisoryTasks();

        printMarket(marketSupervisoryTasks);
        printExpert(expertSupervisoryTasks);
    }

    //打印某一专家的任务
    public void printExpertTask(String expertId){
        ArrayList<ExpertSampling> expertSupervisoryTasks = getExpertSupervisoryTasks(expertId);
        printExpert(expertSupervisoryTasks);
    }

    //查看某一市场的任务
    public void printMarketTask(String marketId){
        ArrayList<AgriMarketSupervisionTask> agriMarketSupervisionTaskArrayList = getBasicSupervisionTasks(marketId);
        printMarketMessage(agriMarketSupervisionTaskArrayList);
    }

    //获取某一市场任务
    public ArrayList<AgriMarketSupervisionTask> getMarketTask(String marketId){
        ArrayList<AgriMarketSupervisionTask> agriMarketSupervisionTaskArrayList = getBasicSupervisionTasks(marketId);
        return agriMarketSupervisionTaskArrayList;
    }

    //获取某一专家的任务
    public ArrayList<ExpertSampling> getExpertSupervisoryTasks(String expertId){
        ArrayList<ExpertSampling> tasksOfExpert = new ArrayList<>();

        ArrayList<ExpertSampling> expertSupervisoryTasks = publishTasks.getExpertSupervisoryTasks();
        for(ExpertSampling expertSampling : expertSupervisoryTasks){
            if((expertSampling.getExpert().getId().toString()).equals(expertId)){
                tasksOfExpert.add(expertSampling);
            }
        }
        return tasksOfExpert;
    }

    //获取某一市场的任务
    public ArrayList<AgriMarketSupervisionTask> getBasicSupervisionTasks(String marketId){
        ArrayList<AgriMarketSupervisionTask> tasksOfMarket = new ArrayList<>();

        ArrayList<BasicSupervisionTasks> basicSupervisionTasks = publishTasks.getMarketSupervisoryTasks();
        AgriMarketSupervisionTask task;
        for(BasicSupervisionTasks basicSupervisionTask : basicSupervisionTasks){
            System.out.println("该任务截止日期为：" + basicSupervisionTask.getDeadline());
            task = basicSupervisionTask.getMarketSupervisionTasks().get(Integer.parseInt(marketId));
            if(task != null) {
                tasksOfMarket.add(task);
            }
        }
        return tasksOfMarket;
    }

    //打印专家任务信息
    private void printExpert(ArrayList<ExpertSampling> expertSupervisoryTasks){
        System.out.println("*****************专家任务信息为*****************");
        for (SupervisoryTask supervisoryTask : expertSupervisoryTasks){
            System.out.println(supervisoryTask.getTaskType() + "任务信息为");
            supervisoryTask.print();
        }
    }

    //打印市场信息
    private void printMarket(ArrayList<BasicSupervisionTasks> marketSupervisoryTasks){
        System.out.println("*****************市场自检任务信息为*****************");
        for (SupervisoryTask supervisoryTask : marketSupervisoryTasks){
            System.out.println(supervisoryTask.getTaskType() + "任务信息为");
            supervisoryTask.print();
        }
    }

    private void printMarketMessage(ArrayList<AgriMarketSupervisionTask> agriMarketSupervisionTaskArrayList){
        for (AgriMarketSupervisionTask agriMarketSupervisionTask : agriMarketSupervisionTaskArrayList){
            if(!agriMarketSupervisionTask.isFinished){
                Set<AgriculturalProduct> unFinishedProducts = agriMarketSupervisionTask.getUnFinishedProducts();
                System.out.println("未完成的类别：");
                for (AgriculturalProduct agriculturalProduct : unFinishedProducts){
                    System.out.println(agriculturalProduct.getName());
                }
            }
            else {
                System.out.println("已完成");
            }

            System.out.println("抽检项报告为：");

            ArrayList<SamplingReport> samplingReportArrayList = agriMarketSupervisionTask.getSamplingReportArrayList();
            for(SamplingReport samplingReport : samplingReportArrayList){
                System.out.println("检测项为："+samplingReport.getRecordProductType());
                System.out.println("检测不合格数为："+samplingReport.getUnqualifiedNum());
                System.out.println("信息录入时间为："+samplingReport.getRecordDate());
            }
        }
    }
}
