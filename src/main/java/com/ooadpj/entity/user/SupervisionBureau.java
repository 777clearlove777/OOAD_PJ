package com.ooadpj.entity.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: 杜东方/孟超
 * @date: 2021/1/4
 * @description: 监管局
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SupervisionBureau {
    /**
     * 编号
     */
    private Integer id;
    /**
     * 名称
     */
    private String name;
}
