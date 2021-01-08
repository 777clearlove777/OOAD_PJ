package com.ooadpj;

import com.ooadpj.service.agriMarketService.MarketAssessTasks;
import com.ooadpj.service.commonService.CheckTasks;
import com.ooadpj.service.expertService.ExpertAssessTasks;
import com.ooadpj.service.supervisionBureauService.InquireScore;
import com.ooadpj.service.supervisionBureauService.PublishTasks;
import com.ooadpj.service.supervisionBureauService.Unqualified;

import java.util.Scanner;

/**
 * @author: 杜东方/孟超
 * @date: 2021/1/4
 * @description: 程序主入口
 */
public class MainApplication {

    private static CheckTasks checkTasks = new CheckTasks();
    private static PublishTasks publishTasks = new PublishTasks();
    private static ExpertAssessTasks expertAssessTasks = new ExpertAssessTasks();
    private static MarketAssessTasks marketAssessTasks = new MarketAssessTasks();
    private static InquireScore inquireScore = new InquireScore();
    private static Unqualified unqualified = new Unqualified();

    public static void main(String[] args) throws Exception {

        //程序入口，选择身份（监管局、专家或农贸市场）
        Scanner scanner = new Scanner(System.in);

        while (true){

            System.out.println("请选择身份：\n1 监管局 \n2 专家 \n3 农贸市场负责人 \nq 退出");
            String identityType = scanner.nextLine();

            switch (identityType){
                //监管局
                case "1":
                    System.out.println(
                            "请选择操作类型：\n1 发布监管任务 \n2 查看任务完成情况 \n3 查看专家得分 \n4 查看市场得分 \n5 查看某一产品的不合格数");
                    String operationType = scanner.nextLine();
                    if(operationType.equals("1")){
                        //分配任务
                        publishTasks.publishTask();
                    }
                    if(operationType.equals("2")){
                        //查看任务完成情况
                        checkTasks.printTask();
                    }
                    if(operationType.equals("3")){
                        //查看专家得分
                        inquireScore.expertScore();
                    }
                    if(operationType.equals("4")){
                        //查看市场得分
                        inquireScore.marketScore();
                    }
                    if(operationType.equals("5")){
                        //查看不合格数
                        unqualified.unqualified();
                    }
                    break;
                //专家
                case "2":
                    System.out.println("请输入您的ID：");
                    String expertId = scanner.nextLine();
                    System.out.println("请选择操作类型：\n1 查看监管任务 \n2 录入检查信息");
                    String operation = scanner.nextLine();

                    if(operation.equals("1")){
                        //查看任务
                        checkTasks.printExpertTask(expertId);
                    }
                    if(operation.equals("2")){
                        //录入检查信息
                        expertAssessTasks.expertAssessTasks(expertId);
                    }
                    break;
                //农贸市场
                case "3":
                    System.out.println("请输入您的市场ID：");
                    String marketId = scanner.nextLine();
                    System.out.println("请选择操作类型：\n1 查看检测任务 \n2 录入检查信息");
                    String op = scanner.nextLine();

                    if(op.equals("1")){
                        //查看任务
                        checkTasks.printMarketTask(marketId);
                    }
                    if(op.equals("2")){
                        //录入检查信息
                        marketAssessTasks.marketAssessTasks(marketId);
                    }
                    break;
                case "q":
                    System.exit(0);
                default:
                    System.out.println("身份输入有误！");
            }

        }


    }
}
