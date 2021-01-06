package com.ooadpj.entity.task;

import com.ooadpj.entity.SamplingReport;
import com.ooadpj.entity.product.AgriculturalProduct;
import com.ooadpj.entity.user.AgriculturalMarket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Set;

/**
 * @author: 杜东方
 * @date: 2021/1/4
 * @description: 农贸市场监管任务，与农贸市场直接关联
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AgriMarketSupervisionTask {
    /**
     * 关联的农贸市场
     */
    private AgriculturalMarket agriculturalMarket;
    /**
     * 1.指定农贸市场监管任务(自检)或2.该专家针对指定农贸市场的抽检的是否完成
     */
    public boolean isFinished;
    /**
     * 该市场未完成的抽检类别
     */
    private Set<AgriculturalProduct> unFinishedProducts;

    /**
     * 抽检项报告
     */
//    private SamplingReport samplingReport;
    private ArrayList<SamplingReport> samplingReportArrayList;
}
