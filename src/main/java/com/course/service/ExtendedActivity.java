package com.course.service;

import com.course.dao.PointDATA;
import com.course.pojo.PointObject;
import com.course.utils.FileUtils;
import com.course.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lixuy
 * Created on 2019-04-11
 *
 * 本类用于扩展活动相关的积分处理逻辑，
 * 要确保类名与 controller 层中需要拦截的方法一致。
 */
@Service
public class ExtendedActivity {

    @Autowired
    private PointDATA pointDATA;

    public void extendedActivity() {
        // 模拟触发扩展活动积分处理逻辑
        System.out.println("***** extendedActivity积分计算方法执行 *****");

        // 获取当前用户的积分对象
        PointObject point = pointDATA.getPoint();
        if (point == null) {
            // 积分对象为空，输出提示信息
            System.out.println("未获取到有效积分数据，终止处理。");
            return;
        }

        // 累加积分：此处为第0类行为添加5分
        pointDATA.addScore(0, 5);
    }

}
