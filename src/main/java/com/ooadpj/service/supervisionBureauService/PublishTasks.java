package com.ooadpj.service.supervisionBureauService;

import com.ooadpj.entity.Grade;
import com.ooadpj.entity.SamplingReport;
import com.ooadpj.entity.product.AgriculturalProduct;
import com.ooadpj.entity.task.AgriMarketSupervisionTask;
import com.ooadpj.entity.task.BasicSupervisionTasks;
import com.ooadpj.entity.task.TaskType;
import com.ooadpj.entity.user.AgriculturalMarket;
import com.ooadpj.service.commonService.DatabaseQuery;
import com.ooadpj.service.commonService.UtilService;

import java.util.*;

/**
 * @author: 孟超
 * @date: 2021/1/6
 * @description: 监管局指定监管任务
 */
public class PublishTasks {

    private Scanner scanner = new Scanner(System.in);
    private DatabaseQuery databaseQuery;
    private UtilService utilService = new UtilService();
    private TaskType taskType;

    //------------发布任务所用属性-----------------

    //市场自检任务
    private static BasicSupervisionTasks basicSupervisionTasks;
    //发布任务选择的市场
    private static Map<Integer, AgriculturalMarket> agriculturalMarketMap = new HashMap<>();

    //------------发布任务所用属性-----------------

    public void publishTask() throws Exception {
        System.out.println("请选择发布类型：1 市场自检 2 专家抽检 3 市场自检和专家抽检");

        String type = scanner.nextLine();
        switch (type){
            case "1":
                marketSelfCheck();
            case "2":
                expertCheck();
            case "3":
                allTypeCheck();
        }
    }

    private void marketSelfCheck() throws Exception {
        //任务id
        String taskId =  utilService.getId();
        //任务名称
        System.out.println("请输入任务名称：");
        String taskName = scanner.nextLine();
        //任务描述
        System.out.println("请输入任务描述：");
        String description = scanner.nextLine();
        //任务截止日期
        System.out.println("请输入任务截止日期：（形式 yyyy-MM-dd，如2020-01-06）");
        String time = scanner.nextLine();
        Date deadline = utilService.getDeadline(time);
        //评分
        Map<Integer, Grade> marketGrades = new HashMap<>();

        //生成自检任务
        basicSupervisionTasks = new BasicSupervisionTasks(taskType,taskId,taskName,description,deadline);

        databaseQuery = new DatabaseQuery();
        System.out.println("请选择本次任务分配市场：(输入非数字结束选择)");
        List<AgriculturalMarket> agriculturalMarkets = databaseQuery.marketsQuery();
        Map<Integer,AgriculturalMarket> map = new HashMap<>();
        for(AgriculturalMarket agriculturalMarket: agriculturalMarkets){
            System.out.println(agriculturalMarket.getId()+"---->"+agriculturalMarket.getName());
            map.put(agriculturalMarket.getId(),agriculturalMarket);
        }

        while (scanner.hasNextInt()){
            System.out.println("=====");
            int marketId = scanner.nextInt();
            Grade grade = new Grade(0,new ArrayList<String>());
            agriculturalMarketMap.put(marketId,map.get(marketId));
            //初始化市场得分记录
            marketGrades.put(marketId,grade);
        }
        scanner.next();

        //获取产品类别
        List<AgriculturalProduct> agriculturalProductList = databaseQuery.productQuery();
        Map<Integer,AgriculturalProduct> productMap = new HashMap<>();
        System.out.println("请为每个市场分配调查任务：");
        Map<Integer, AgriMarketSupervisionTask> agriMarketSupervisionTaskMap = new HashMap<>();
        Set<Integer> keySet = agriculturalMarketMap.keySet();

        //开始迭代key集合
        Iterator<Integer> it = keySet.iterator();
        while (it.hasNext()) {
            Integer key = it.next();
            AgriMarketSupervisionTask agriMarketSupervisionTask = new AgriMarketSupervisionTask();

            agriMarketSupervisionTask.setFinished(false);
            agriMarketSupervisionTask.setAgriculturalMarket(map.get(key));
            //选择待检测产品类别
            System.out.println("请选择"+map.get(key).getName()+"检查产品:(输入非数字结束选择)");
            for (AgriculturalProduct agriculturalProduct: agriculturalProductList){
                System.out.println(agriculturalProduct.getId()+"----->"+agriculturalProduct.getName());
                productMap.put(agriculturalProduct.getId(),agriculturalProduct);
            }

            Set<AgriculturalProduct> productSet = new HashSet<>();
            ArrayList<SamplingReport> samplingReportArrayList = new ArrayList<>();
            while (scanner.hasNextInt()){
                int productId = scanner.nextInt();
                AgriculturalProduct agriculturalProduct = productMap.get(productId);
                productSet.add(agriculturalProduct);
                //生成对应农产品的报告
                SamplingReport samplingReport = new SamplingReport(null,agriculturalProduct.getName(),0);
                samplingReportArrayList.add(samplingReport);
            }
            scanner.next();
            agriMarketSupervisionTask.setUnFinishedProducts(productSet);
            agriMarketSupervisionTask.setSamplingReportArrayList(samplingReportArrayList);

            //每个市场的任务放入总任务集合中
            agriMarketSupervisionTaskMap.put(key, agriMarketSupervisionTask);
        }
        basicSupervisionTasks.setMarketGrades(marketGrades);
        basicSupervisionTasks.setMarketSupervisionTasks(agriMarketSupervisionTaskMap);
    }

    private void expertCheck(){

    }

    private void allTypeCheck(){

    }


    public BasicSupervisionTasks getBasicSupervisionTasks(){
        return basicSupervisionTasks;
    }

    public static Map<Integer, AgriculturalMarket> getAgriculturalMarketMap() {
        return agriculturalMarketMap;
    }
}
