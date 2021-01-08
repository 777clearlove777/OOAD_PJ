package com.ooadpj.service.supervisionBureauService;

import com.ooadpj.entity.Grade;
import com.ooadpj.entity.SamplingReport;
import com.ooadpj.entity.product.AgriculturalProduct;
import com.ooadpj.entity.task.*;
import com.ooadpj.entity.user.AgriculturalMarket;
import com.ooadpj.entity.user.Expert;
import com.ooadpj.service.commonService.DatabaseQuery;
import com.ooadpj.service.commonService.UtilService;

import java.util.*;

/**
 * @author: 孟超
 * @date: 2021/1/6
 * @description: 监管局指定监管任务
 */
public class PublishTasks {

    private DatabaseQuery databaseQuery = new DatabaseQuery();
    private final UtilService utilService = new UtilService();

    //存储市场任务
    private static ArrayList<BasicSupervisionTasks> marketSupervisoryTasks = new ArrayList<>();
    //专家任务
    private static ArrayList<ExpertSampling> expertSupervisoryTasks = new ArrayList<>();

    //市场自检所选择的市场集合
    private static Map<Integer, AgriculturalMarket> agriculturalMarketMap = new HashMap<>();
    //专家所选市场集合
    private static Map<Integer, AgriculturalMarket> agriculturalMarketMapOfExpert = new HashMap<>();

    public void publishTask() throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请选择发布类型：1 市场自检 2 专家抽检 3 市场自检和专家抽检");

        String type = scanner.nextLine();
        switch (type){
            case "1":
                marketSelfCheck();break;
            case "2":
                expertCheck();break;
            case "3":
                allTypeCheck();break;
        }
    }

    private void marketSelfCheck() throws Exception {

        String[] properties = getTaskProperties();
        //生成自检任务
        BasicSupervisionTasks basicSupervisionTasks =
                getBasicSupervisionTasks(properties[0],properties[1],properties[2],utilService.getDeadline(properties[3]));

        //打印所有市场并返回市场集合map
        Map<Integer,AgriculturalMarket> map =getMap();

        //获取评分
        Map<Integer, Grade> marketGrades = getMarketGrades(map);

        //每个市场任务集合获取
        Map<Integer, AgriMarketSupervisionTask> agriMarketSupervisionTaskMap =
                getAgriMarketSupervisionTaskMap(map,agriculturalMarketMap);

        basicSupervisionTasks.setMarketGrades(marketGrades);
        basicSupervisionTasks.setMarketSupervisionTasks(agriMarketSupervisionTaskMap);

        marketSupervisoryTasks.add(basicSupervisionTasks);

    }

    private void expertCheck() throws Exception {

        //专家任务
        ExpertSampling expertSampling;

        //获取任务基本属性
        String[] properties = getTaskProperties();

        //评分
        Grade expertGrade = getGrade();
        //选择专家
        Expert expert = getExpert();

        //生成专家任务任务
        expertSampling = getExpertSampling(
                properties[0],properties[1],properties[2],utilService.getDeadline(properties[3]),expert);

        Map<Integer,AgriculturalMarket> map = getMap();
        expertSelectMarket(map);

        //每个市场任务集合获取
        Map<Integer, AgriMarketSupervisionTask> agriMarketSupervisionTaskMap =
                getAgriMarketSupervisionTaskMap(map,agriculturalMarketMapOfExpert);

        expertSampling.setExpertGrade(expertGrade);
        expertSampling.setMarketSupervisionTasks(agriMarketSupervisionTaskMap);

        expertSupervisoryTasks.add(expertSampling);
    }

    private void allTypeCheck() throws Exception {
        System.out.println("****************市场自检任务发布****************");
        marketSelfCheck();
        System.out.println("****************专家任务发布****************");
        expertCheck();
    }


    //返回任务基本属性
    private String[] getTaskProperties(){
        String[] properties = new String[4];
        Scanner scanner = new Scanner(System.in);
        //任务id
        properties[0] =  utilService.getId();
        //任务名称
        System.out.println("请输入任务名称：");
        properties[1] = scanner.nextLine();
        //任务描述
        System.out.println("请输入任务描述：");
        properties[2] = scanner.nextLine();
        //任务截止日期
        System.out.println("请输入任务截止日期：（形式 yyyy-MM-dd，如2020-01-06）");
        properties[3] = scanner.nextLine();

        return properties;
    }

    //生成市场自检任务
    private BasicSupervisionTasks getBasicSupervisionTasks(
            String taskId, String taskName, String description, Date deadline){
        return new BasicSupervisionTasks(TaskType.ExpertSampling,taskId,taskName,description,deadline);
    }

    //选择检测市场，返回市场任务评分集合
    private Map<Integer, Grade> getMarketGrades(Map<Integer,AgriculturalMarket> map) {
        Scanner scanner = new Scanner(System.in);
        //评分
        agriculturalMarketMap.clear();
        Map<Integer, Grade> marketGrades = new HashMap<>();
        System.out.println("请选择本次任务分配市场：(输入非数字结束选择)");
        while (scanner.hasNextInt()){
            int marketId = scanner.nextInt();
            Grade grade = new Grade(0,new ArrayList<String>());
            agriculturalMarketMap.put(marketId,map.get(marketId));
            //初始化市场得分记录
            marketGrades.put(marketId,grade);
        }

        return marketGrades;
    }

    private void expertSelectMarket(Map<Integer,AgriculturalMarket> map){
        agriculturalMarketMapOfExpert.clear();

        Scanner scanner = new Scanner(System.in);
        System.out.println("请选择本次任务分配市场：(输入非数字结束选择)");
        while (scanner.hasNextInt()){
            int marketId = scanner.nextInt();
            agriculturalMarketMapOfExpert.put(marketId,map.get(marketId));
        }
    }

    private Map<Integer, AgriMarketSupervisionTask> getAgriMarketSupervisionTaskMap(
            Map<Integer,AgriculturalMarket> map, Map<Integer, AgriculturalMarket> MarketMap) throws Exception {

        Scanner scanner = new Scanner(System.in);
        //获取产品类别
        List<AgriculturalProduct> agriculturalProductList = databaseQuery.productQuery();
        Map<Integer,AgriculturalProduct> productMap = new HashMap<>();
        System.out.println("请为每个市场分配调查任务：");

        Map<Integer, AgriMarketSupervisionTask> agriMarketSupervisionTaskMap = new HashMap<>();
        Set<Integer> keySet = MarketMap.keySet();

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
        return agriMarketSupervisionTaskMap;
    }

    //打印市场对应信息和返回市场集
    private Map<Integer,AgriculturalMarket> getMap() throws Exception {

        Map<Integer,AgriculturalMarket> map = new HashMap<>();

        List<AgriculturalMarket> agriculturalMarkets = databaseQuery.marketsQuery();
        for(AgriculturalMarket agriculturalMarket: agriculturalMarkets){
            System.out.println(agriculturalMarket.getId()+"---->"+agriculturalMarket.getName());
            map.put(agriculturalMarket.getId(),agriculturalMarket);
        }
        return map;
    }

    //返回专家对应任务评分
    private Grade getGrade(){
        return new Grade(0,new ArrayList<String>());
    }

    //选择专家
    private Expert getExpert() throws Exception {
        Scanner scanner = new Scanner(System.in);
        //选择专家
        databaseQuery = new DatabaseQuery();
        List<Expert> expertList = databaseQuery.expertsQuery();
        Map<Integer,Expert> expertMap = new HashMap<>();
        for(Expert expert : expertList){
            System.out.println(expert.getId()+"---->"+expert.getName());
            expertMap.put(expert.getId(),expert);
        }

        //选择专家
        int expertId = scanner.nextInt();
        return expertMap.get(expertId);
    }

    //生成专家任务
    private ExpertSampling getExpertSampling(
            String taskId, String taskName, String description, Date deadline, Expert expert){

        return new ExpertSampling(TaskType.ExpertSampling,taskId,taskName,description,deadline,expert);
    }

    public ArrayList<ExpertSampling> getExpertSupervisoryTasks() {
        return expertSupervisoryTasks;
    }

    public ArrayList<BasicSupervisionTasks> getMarketSupervisoryTasks() {
        return marketSupervisoryTasks;
    }
}
