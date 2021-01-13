import com.ooadpj.entity.Grade;
import com.ooadpj.entity.product.AquaticProducts;
import com.ooadpj.entity.product.LivestockPoultryMeat;
import com.ooadpj.entity.product.Vegetables;
import com.ooadpj.entity.task.AgriMarketSupervisionTask;
import com.ooadpj.entity.task.ExpertSampling;
import com.ooadpj.entity.user.AgriculturalMarket;
import com.ooadpj.entity.user.Expert;
import com.ooadpj.service.agriMarketService.MarketAssessTasks;
import com.ooadpj.service.commonService.CheckTasks;
import com.ooadpj.service.commonService.DatabaseDao;
import com.ooadpj.service.expertService.ExpertAssessTasks;
import com.ooadpj.service.supervisionBureauService.InquireScore;
import com.ooadpj.service.supervisionBureauService.PublishTasks;
import com.ooadpj.service.supervisionBureauService.Unqualified;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author: 杜东方
 * @date: 2021/1/9
 * @description: 测试用例
 */
public class SimpleTest {

    public static PublishTasks publishTasks;
    public static DatabaseDao databaseDao;
    public static CheckTasks checkTasks;
    public static MarketAssessTasks marketAssessTasks;
    public static ExpertAssessTasks expertAssessTasks;
    public static InquireScore inquireScore;
    public static Unqualified unqualified;


    @BeforeClass
    public static void initTest() throws Exception{
        publishTasks = new PublishTasks();
        checkTasks = new CheckTasks();
        marketAssessTasks = new MarketAssessTasks();
        expertAssessTasks = new ExpertAssessTasks();
        unqualified = new Unqualified();
        inquireScore = new InquireScore();
        databaseDao = DatabaseDao.getInstance();
        //插入市场输入
//        databaseDao.insertMarket(new AgriculturalMarket(1, "market1"));
//        databaseDao.insertMarket(new AgriculturalMarket(2, "market2"));
//        databaseDao.insertMarket(new AgriculturalMarket(3, "market3"));
        //插入农产品
//        databaseDao.insertProduct(new AquaticProducts());
//        databaseDao.insertProduct(new LivestockPoultryMeat());
//        databaseDao.insertProduct(new Vegetables());
        //插入专家
//        databaseDao.insertExpert(new Expert(1, "expert1"));
//        databaseDao.insertExpert(new Expert(2, "expert2"));
//        databaseDao.insertExpert(new Expert(3, "expert3"));

    }

    /**
     * 市场自检任务模块测试
     */
    @Test
    public void testMarketSupervisionTaskModule() throws Exception{
        PublishTasks.agriculturalMarketMap.clear();
        PublishTasks.agriculturalMarketMapOfExpert.clear();
        PublishTasks.expertSupervisoryTasks.clear();
        PublishTasks.marketSupervisoryTasks.clear();
        try {
            System.setIn(new FileInputStream(new File("src/main/resources/testData.txt")));
            //发起市场监管任务
            publishTasks.publishTask();

            //测试任务是否正确发布，农贸市场正常接收任务
            ArrayList<AgriMarketSupervisionTask> agriMarketSupervisionTaskArrayList = checkTasks.getMarketTask("1");
            assert(agriMarketSupervisionTaskArrayList.size() == 1);
            assert(agriMarketSupervisionTaskArrayList.get(0).getUnFinishedProducts().size() == 2);

            //测试市场完成抽检任务
            System.setIn(new FileInputStream(new File("src/main/resources/testData2.txt")));
            marketAssessTasks.marketAssessTasks("1");
            agriMarketSupervisionTaskArrayList = checkTasks.getMarketTask("1");
            assert(agriMarketSupervisionTaskArrayList.get(0).getUnFinishedProducts().size() == 0);

        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 专家任务模块测试
     */
    @Test
    public void testExpertSupervisionTaskModule() throws Exception{
        PublishTasks.agriculturalMarketMap.clear();
        PublishTasks.agriculturalMarketMapOfExpert.clear();
        PublishTasks.expertSupervisoryTasks.clear();
        PublishTasks.marketSupervisoryTasks.clear();
        try {
            System.setIn(new FileInputStream(new File("src/main/resources/testData3.txt")));
            //发起专家抽检任务
            publishTasks.publishTask();

            //测试任务是否正确发布，专家正常接收任务
            ArrayList<ExpertSampling> tasksOfExpert = checkTasks.getExpertSupervisoryTasks("1");
            assert(tasksOfExpert.size() == 1);
            assert(tasksOfExpert.get(0).getMarketSupervisionTasks().get(1).getUnFinishedProducts().size() == 2);
            assert(tasksOfExpert.get(0).getMarketSupervisionTasks().get(3).getUnFinishedProducts().size() == 1);
            //测试专家完成抽检任务
            System.setIn(new FileInputStream(new File("src/main/resources/testData4.txt")));
            expertAssessTasks.expertAssessTasks("1");
            assert(tasksOfExpert.get(0).getMarketSupervisionTasks().get(1).getUnFinishedProducts().size() == 0);
            assert(tasksOfExpert.get(0).getMarketSupervisionTasks().get(3).getUnFinishedProducts().size() == 0);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试某个农产品总的不合格数量
     */
    @Test
    public void testTotalUnqualifiedNumOfProduct() throws Exception{
        try {
            System.setIn(new FileInputStream(new File("src/main/resources/testData.txt")));
            //发起市场监管任务
            publishTasks.publishTask();
            //市场完成抽检任务
            System.setIn(new FileInputStream(new File("src/main/resources/testData2.txt")));
            marketAssessTasks.marketAssessTasks("1");

            System.setIn(new FileInputStream(new File("src/main/resources/testData3.txt")));
            //发起专家抽检任务
            publishTasks.publishTask();
            //专家完成抽检任务
            System.setIn(new FileInputStream(new File("src/main/resources/testData4.txt")));
            expertAssessTasks.expertAssessTasks("1");

            assert(unqualified.getTotalUnqualifiedNumOfProduct("蔬菜水果") == 6);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //测试市场未按时情况
    @Test
    public void testUnFinishedTask_Market() throws Exception{
        System.setIn(new FileInputStream(new File("src/main/resources/testData5.txt")));
        //发起市场监管任务
        publishTasks.publishTask();
        //市场完成抽检任务
        System.setIn(new FileInputStream(new File("src/main/resources/testData2.txt")));
        marketAssessTasks.marketAssessTasks("1");

        Grade grade = inquireScore.getMarketScore(1);
        assert(grade.getGrade() == -30);
    }

    //测试专家未按时情况
    @Test
    public void testUnFinishedTask_Expert() throws Exception{
        System.setIn(new FileInputStream(new File("src/main/resources/testData3.txt")));
        //发起专家抽检任务
        publishTasks.publishTask();
        //专家完成抽检任务
        System.setIn(new FileInputStream(new File("src/main/resources/testData4.txt")));
        expertAssessTasks.expertAssessTasks("1");

        Grade grade = inquireScore.getExpertScore(1);
        assert(grade.getGrade() == -30);
    }
}
