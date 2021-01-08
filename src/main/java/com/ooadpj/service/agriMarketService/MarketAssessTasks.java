package com.ooadpj.service.agriMarketService;

import com.ooadpj.entity.task.AgriMarketSupervisionTask;
import com.ooadpj.service.commonService.CheckTasks;
import com.ooadpj.service.expertService.ExpertAssessTasks;

import java.util.ArrayList;

/**
 * @author: 孟超
 * @date: 2021/1/8
 * @description: 市场进行任务评估
 */
public class MarketAssessTasks {

    public void marketAssessTasks(String marketId){
        CheckTasks checkTasks = new CheckTasks();
        ExpertAssessTasks expertAssessTasks = new ExpertAssessTasks();

        //获取某一市场的监管任务
        ArrayList<AgriMarketSupervisionTask> agriMarketSupervisionTaskArrayList = checkTasks.getBasicSupervisionTasks(marketId);

        //进行数据录入
        for(AgriMarketSupervisionTask agriMarketSupervisionTask : agriMarketSupervisionTaskArrayList){
            expertAssessTasks.marketInput(agriMarketSupervisionTask);
        }

    }
}
