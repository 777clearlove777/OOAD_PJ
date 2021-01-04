package com.ooadpj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author: 杜东方
 * @date: 2021/1/4
 * @description: 抽检项报告
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SamplingReport {
    /**
     * 抽检结果录入日期
     */
    private Date recordDate;
    /**
     * 抽检农产品类别
     */
    private String recordProductType;
    /**
     * 抽检不合格数量
     */
    private int unqualifiedNum;
}
