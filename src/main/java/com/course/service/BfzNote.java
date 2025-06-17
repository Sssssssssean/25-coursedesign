package com.course.service;
import com.course.dao.PointDATA;
import com.course.model.Result;
import com.course.pojo.PointObject;
import com.course.utils.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.pojo.PointObject;
import com.course.utils.FileUtils;
import com.course.utils.JsonUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author lixuy
 * Created on 2019-04-11
 */
//类名与方法名须与controller层拦截的方法一致
@Service
public class BfzNote {
    @Autowired
    private PointDATA pointDATA;

    public void BfzNode() {
        System.out.println("+++++bfzNote积分计算方法执行+++++");
        PointObject point = pointDATA.getPoint();
        LocalDateTime lastBFZtime = LocalDateTime.parse(point.getLastComplicationTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime now = LocalDateTime.now();
        // 一年记一次分
        if (!lastBFZtime.plusYears(1).isBefore(now)) {
            System.err.println("距离上次填写并发症不足一年");
            return ;
        }
        // 计分
        pointDATA.addScore(3, 0);
        PointObject dataPoint = pointDATA.getPoint();
        dataPoint.setLastComplicationTime(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        pointDATA.setPoint(dataPoint);
    }
}