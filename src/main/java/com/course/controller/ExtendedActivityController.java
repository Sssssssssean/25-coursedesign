package com.course.controller;

import com.course.model.Result;
import com.course.service.ExtendedActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RestController;
import com.course.model.Result;
import org.springframework.stereotype.Component;

/**
 * 控制器组件：用于处理用户参与扩展活动的操作。
 * 示例方法仅用于模拟行为，可结合 AOP 或其他机制进行拦截测试。
 */
@RestController
public class ExtendedActivityController {

    @Autowired
    private ExtendedActivity extendedActivity;

    @GetMapping("/extendedActivity")
    public Result extendedActivity() {
        System.out.println("======被拦截的extendedActivity方法执行======");
        extendedActivity.extendedActivity();
        return Result.success("参加扩展活动成功");
    }

}