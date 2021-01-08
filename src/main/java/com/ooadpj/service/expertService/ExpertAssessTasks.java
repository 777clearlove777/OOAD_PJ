package com.ooadpj.service.expertService;


import com.ooadpj.entity.SamplingReport;
import com.ooadpj.entity.product.AgriculturalProduct;
import com.ooadpj.entity.task.AgriMarketSupervisionTask;
import com.ooadpj.entity.task.ExpertSampling;
import com.ooadpj.service.commonService.CheckTasks;

import java.util.*;

/**
 * @author: 孟超
 * @date: 2021/1/6
 * @description: 专家评估任务
 */
public class ExpertAssessTasks {

    public void expertAssessTasks(String expertId){

        CheckTasks checkTasks = new CheckTasks();

        //获取专家任务
        ArrayList<ExpertSampling> expertSamplingArrayList = checkTasks.getExpertSupervisoryTasks(expertId);

        //进行每个市场的检测结果信息录入
        for(ExpertSampling expertSampling : expertSamplingArrayList){

            //获取每个市场的任务
            Map<Integer, AgriMarketSupervisionTask> agriMarketSupervisionTaskMap = expertSampling.getMarketSupervisionTasks();

            //市场结果录入
            marketsMessage(agriMarketSupervisionTaskMap);
        }

    }

    //某一专家负责的所有农贸市场的结果录入
    private void marketsMessage(Map<Integer, AgriMarketSupervisionTask> agriMarketSupervisionTaskMap){
        for (AgriMarketSupervisionTask agriMarketSupervisionTask : agriMarketSupervisionTaskMap.values()){
            //对其中的每一个进行操作
            if(!agriMarketSupervisionTask.isFinished){
                marketInput(agriMarketSupervisionTask);
                //完成后将本市场设为已完成状态
                agriMarketSupervisionTask.setFinished(true);
            }
        }
    }

    //具体操作某一市场
    public void marketInput(AgriMarketSupervisionTask agriMarketSupervisionTask){
        //获取抽检项报告
        ArrayList<SamplingReport> samplingReportArrayList = agriMarketSupervisionTask.getSamplingReportArrayList();

        System.out.println("您当前操作市场为："+agriMarketSupervisionTask.getAgriculturalMarket().getName());

        for (SamplingReport samplingReport : samplingReportArrayList){
            //抽检项报告录入成功
            if(samplingReportInput(samplingReport)){
                //去除当前市场的未完成项
                deleteUnFinishedProducts(agriMarketSupervisionTask,samplingReport.getRecordProductType());
            }
        }
    }

    //录入当前产品的检查结果
    private boolean samplingReportInput(SamplingReport samplingReport){
        try{
            Scanner scanner = new Scanner(System.in);
            System.out.println("录入产品为："+samplingReport.getRecordProductType()+"\n请输入当前产品的不合格数:");
            int unqualifiedNum = scanner.nextInt();

            //获取当前时间
            Date currentDate = getDate();

            samplingReport.setRecordDate(currentDate);
            samplingReport.setUnqualifiedNum(unqualifiedNum);
            return true;

        }catch(Exception exception){
            return false;
        }

    }

    //去除已完成录入的产品
    private void deleteUnFinishedProducts(AgriMarketSupervisionTask agriMarketSupervisionTask, String productName){
        Set<AgriculturalProduct> unFinishedProducts = new HashSet<>();

        for(AgriculturalProduct agriculturalProduct : agriMarketSupervisionTask.getUnFinishedProducts()){
            if(!((agriculturalProduct.getName()).equals(productName))){
                unFinishedProducts.add(agriculturalProduct);
            }
        }
        agriMarketSupervisionTask.setUnFinishedProducts(unFinishedProducts);
    }

//    获取日期
    private Date getDate(){
        return new Date();
    }
}
