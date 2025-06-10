package com.course.coursedesign.dao;

import com.course.coursedesign.pojo.PointObject;

import java.util.List;

public interface PointDATA {
    // setScore 设置积分对象
    void setPoint(PointObject pointObject);

    // 获取积分对象
    PointObject getPoint();

    // 获取成长分数
    int getGrowScore();

    // 获取可兑换分数
    int getExchangeScore();

    // 增加分数 growScore为成长积分 exchangeScore为可兑换积分
    void addScore(int growScore, int exchangeScore);

    PointObject getPointObjectById(int id);

    List<PointObject> getAllPointObjects();
}