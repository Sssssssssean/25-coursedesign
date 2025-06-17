package com.course.controller;

import com.course.model.Result;
import com.course.service.EvaluateReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Component;

/**
 * @author lixuy
 * Created on 2019-04-11
 */
//生成评估报告
@RestController
public class EvaluateReportController {
    @Autowired
    private EvaluateReport evaluateReport;

    @GetMapping("/evaluateReport")
    public Result<String> evaluateReport() {
        System.out.println("======被拦截的evaluateReport方法执行======");
        evaluateReport.evaluate();
        return Result.success("evaluateReport completed");
    }
}






