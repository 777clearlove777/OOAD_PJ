package com.ooadpj.service.supervisionBureauService;

import com.ooadpj.entity.SamplingReport;
import com.ooadpj.entity.product.ProductType;
import com.ooadpj.entity.task.AgriMarketSupervisionTask;
import com.ooadpj.entity.task.BasicSupervisionTasks;
import com.ooadpj.entity.task.ExpertSampling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: 孟超
 * @date: 2021/1/8
 * @description: 统计当前的产品不合格总数
 */
public class Unqualified {

    private static Map<String,Integer> marketResult = new HashMap<String, Integer>(){{
        put(ProductType.AQUATICPRODUCTS,0);
        put(ProductType.LIVESTOCKPOULTRYMEAT,0);
        put(ProductType.VEGETABLES,0);
    }};

    private static Map<String,Integer> expertResult = new HashMap<String, Integer>(){{
        put(ProductType.AQUATICPRODUCTS,0);
        put(ProductType.LIVESTOCKPOULTRYMEAT,0);
        put(ProductType.VEGETABLES,0);
    }};

    public void unqualified(){

        PublishTasks publishTasks = new PublishTasks();

        //获取所有的市场自检任务
        ArrayList<BasicSupervisionTasks> marketSupervisoryTasks = publishTasks.getMarketSupervisoryTasks();
        //获取所有的专家检查任务
        ArrayList<ExpertSampling> expertSupervisoryTasks = publishTasks.getExpertSupervisoryTasks();

        System.out.println("***********市场任务统计结果***************");
        //对市场自检的任务进行统计
        marketStatistics(marketSupervisoryTasks);

        System.out.println("***********专家任务统计结果***************");
        //专家统计
        expertStatistics(expertSupervisoryTasks);

    }

    //市场统计
    private void marketStatistics(ArrayList<BasicSupervisionTasks> marketSupervisoryTasks){

        for(BasicSupervisionTasks basicSupervisionTask : marketSupervisoryTasks){
            //对每个市场任务进行分别统计
            System.out.println("任务ID：" + basicSupervisionTask.getId());
            //该任务包含的市场自检任务
            Map<Integer,AgriMarketSupervisionTask> agriMarketSupervisionTaskArrayList = basicSupervisionTask.getMarketSupervisionTasks();

            //每一个进行统计
            for(AgriMarketSupervisionTask agriMarketSupervisionTask : agriMarketSupervisionTaskArrayList.values()){
                marketSumOfProduct(agriMarketSupervisionTask);
            }
            printMarketResult();
            marketResult.clear();
        }
    }

    //专家统计
    private void expertStatistics(ArrayList<ExpertSampling> expertSupervisoryTasks){

        for (ExpertSampling expertSampling : expertSupervisoryTasks){
            //对每个专家任务进行统计
            System.out.println("任务ID：" + expertSampling.getId());

            //该任务包含的市场自检任务
            Map<Integer,AgriMarketSupervisionTask> agriMarketSupervisionTaskArrayList = expertSampling.getMarketSupervisionTasks();

            //每一个进行统计
            for(AgriMarketSupervisionTask agriMarketSupervisionTask : agriMarketSupervisionTaskArrayList.values()){
                expertSumOfProduct(agriMarketSupervisionTask);
            }
            printExpertResult();
            expertResult.clear();
        }
    }


    private void printMarketResult(){
        for (Map.Entry<String, Integer> entry : marketResult.entrySet()) {
            System.out.println(entry.getKey() + ", 不合格数 = " + entry.getValue());
        }
    }

    private void printExpertResult(){
        for (Map.Entry<String, Integer> entry : expertResult.entrySet()) {
            System.out.println(entry.getKey() + ", 不合格数 = " + entry.getValue());
        }
    }

    private void marketSumOfProduct(AgriMarketSupervisionTask agriMarketSupervisionTask){

        for(SamplingReport samplingReport : agriMarketSupervisionTask.getSamplingReportArrayList()){
            String productType = samplingReport.getRecordProductType();
            marketResult.put(productType, marketResult.get(productType)+samplingReport.getUnqualifiedNum());
        }

    }
    private void expertSumOfProduct(AgriMarketSupervisionTask agriMarketSupervisionTask){

        for(SamplingReport samplingReport : agriMarketSupervisionTask.getSamplingReportArrayList()){
            String productType = samplingReport.getRecordProductType();
            expertResult.put(productType, expertResult.get(productType)+samplingReport.getUnqualifiedNum());
        }

    }
}
