package com.ooadpj.entity.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: 杜东方
 * @date: 2021/1/4
 * @description: 农产品
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public abstract class AgriculturalProduct {
    /**
     * 分类编号
     */
    private Integer id;
    /**
     * 产品名称
     */
    private String name;

}
