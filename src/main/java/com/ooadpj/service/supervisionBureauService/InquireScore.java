package com.ooadpj.service.supervisionBureauService;

import com.ooadpj.entity.Grade;
import com.ooadpj.entity.task.BasicSupervisionTasks;
import com.ooadpj.entity.task.ExpertSampling;
import com.ooadpj.service.commonService.CheckTasks;

import java.util.ArrayList;
import java.util.Scanner;

public class InquireScore {

    public void marketScore(){

        PublishTasks publishTasks = new PublishTasks();
        CheckTasks checkTasks = new CheckTasks();

        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入您要查看的市场ID");
        String marketId = scanner.nextLine();

        //获取所有市场任务
        ArrayList<BasicSupervisionTasks> marketSupervisoryTasks = publishTasks.getMarketSupervisoryTasks();
        for (BasicSupervisionTasks basicSupervisionTasks : marketSupervisoryTasks){
            System.out.println(basicSupervisionTasks.getName()+"++++++++++++++++");
            //评估
            basicSupervisionTasks.taskAssess();
            //获取分数记录
            Grade marketGrade = basicSupervisionTasks.getMarketGrades().get(Integer.parseInt(marketId));

            printMessageOfMarket(marketGrade);

        }

    }

    private void printMessageOfMarket(Grade marketGrade){
        System.out.println("该市场得分为："+ marketGrade.getGrade());
        System.out.println("具体细节为：");
        for (String s :marketGrade.getGradeRecord()){
            System.out.println(s);
        }

    }

    public void expertScore(){
        CheckTasks checkTasks = new CheckTasks();

        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入您要查看的专家ID");
        String expertId = scanner.nextLine();

        //获取专家任务
        ArrayList<ExpertSampling> expertSamplingArrayList = checkTasks.getExpertSupervisoryTasks(expertId);

        //输出专家分数
        printExpertScore(expertSamplingArrayList);
    }

    private void printExpertScore(ArrayList<ExpertSampling> expertSamplingArrayList){

        for(ExpertSampling expertSampling : expertSamplingArrayList){
            //进行评估
            expertSampling.taskAssess();
            printMessage(expertSampling);
        }
    }

    private void printMessage(ExpertSampling expertSampling){
        System.out.println("本次任务专家为："+ expertSampling.getExpert().getName());
        System.out.println("该任务专家得分为："+ expertSampling.getExpertGrade().getGrade());
        System.out.println("具体细节为：");
        for (String s : expertSampling.getExpertGrade().getGradeRecord()){
            System.out.println(s);
        }

    }
}
