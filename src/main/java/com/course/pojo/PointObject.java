package com.course.pojo;

import java.io.Serializable;

/**
 * @author lixuy
 * Created on 2019-04-10
 */
//实体类
public class PointObject {
    private Integer id;
    //成长积分数
    private Integer growScore;
    //可兑换积分数
    private Integer exchangeScore;
    //总积分数
    private Integer scoreTotal;
    // 上次登录时间
    private String lastLoginTime;
    // 最近并发症计分
    private String lastComplicationTime;
    //最近胰岛素检测成功时间
    private String lastYdqnTime;
    // 是否完善个人信息
    private Boolean profileInputed;
    //血糖记录次数
    private Integer bloodSugarCount;
    // 积分记录
    private List<ScoreRecord> scoreRecords;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGrowScore() {
        return growScore;
    }

    public void setGrowScore(Integer growScore) {
        this.growScore = growScore;
    }

    public Integer getExchangeScore() {
        return exchangeScore;
    }

    public void setExchangeScore(Integer exchangeScore) {
        this.exchangeScore = exchangeScore;
    }

    public Integer getScoreTotal() {
        return scoreTotal;
    }

    public void setScoreTotal(Integer scoreTotal) {
        this.scoreTotal = scoreTotal;
    }
}
