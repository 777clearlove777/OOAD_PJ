package com.ooadpj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

/**
 * @author: 杜东方
 * @date: 2021/1/4
 * @description: 得分及纪录
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Grade {
    /**
     * 得分
     */
    private int grade;
    /**
     * 得分记录——如: +10 按时完成
     */
    private ArrayList<String> gradeRecord;
}
