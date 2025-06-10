package com.course.coursedesign.controller;

import com.course.coursedesign.model.Result;
import com.course.coursedesign.service.FollowUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FollowUpController {
    @Autowired
    private FollowUp followUp;

    @GetMapping("/followUp")
    public Result followup(){
        System.out.println("followup 执行");
        followUp.followUp();
        return Result.success("FollowUp completed");
    }
}
