package com.course.controller;

import com.course.model.Result;
import org.springframework.stereotype.Component;

/**
 * 控制器组件：用于处理用户参与扩展活动的操作。
 * 示例方法仅用于模拟行为，可结合 AOP 或其他机制进行拦截测试。
 */
@Component
public class ExtendedActivity {

    public Result<String> extendedActivity() {
        // 控制台打印提示信息，用于模拟方法执行
        System.out.println(">>> 执行了 extendedActivity 方法（此方法可能被拦截） <<<");
        // 返回处理结果，这里假设调用某个通用返回对象
        return Result.success("参加扩展活动成功");
    }

}
