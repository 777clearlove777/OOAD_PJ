package com.ooadpj.service.commonService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author: 孟超
 * @date: 2021/1/8
 * @description: 公共的函数
 */
public class UtilService {

    public String getId(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "");
    }

    public Date getDeadline(String time) throws ParseException {
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        Date deadLine = dateFormat1.parse(time);

        return deadLine;
    }
}
