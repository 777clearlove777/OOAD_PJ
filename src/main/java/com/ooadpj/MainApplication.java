package com.ooadpj;

import com.ooadpj.service.supervisionBureauService.CheckTasks;
import com.ooadpj.service.supervisionBureauService.PublishTasks;

import java.util.Scanner;

/**
 * @author: 杜东方/孟超
 * @date: 2021/1/4
 * @description:
 */
public class MainApplication {

    private static PublishTasks publishTasks;
    private static CheckTasks checkTasks;

    public static void main(String[] args) throws Exception {

        //程序入口，选择身份（监管局、专家或农贸市场）
        Scanner scanner = new Scanner(System.in);

        while (true){
            System.out.println("请选择身份：\n1 监管局 \n2 专家 \n3 农贸市场负责人 \nq 退出");
            String identityType = scanner.nextLine();

            switch (identityType){
                //监管局
                case "1":
                    publishTasks = new PublishTasks();

                    System.out.println("请选择操作类型：\n1 发布监管任务 \n2 查看任务完成情况");
                    String operationType = scanner.nextLine();
                    if(operationType.equals("1")){
                        //分配任务
                        publishTasks.publishTask();
                    }

                    if(operationType.equals("2")){
                        checkTasks = new CheckTasks();
                        //查看任务完成情况
                        checkTasks.printTask();
                    }
                    break;
                //专家
                case "2":

                    break;
                //农贸市场
                case "3":
                    break;
                case "q":
                    System.exit(0);
                default:
                    System.out.println("身份输入有误！");
            }

        }


    }
}
