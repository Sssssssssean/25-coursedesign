package com.course.controller;

import org.springframework.stereotype.Component;
import com.course.coursedesign.model.Result;
import com.course.coursedesign.service.BfzNote;
import com.course.coursedesign.service.BloodSugar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BloodSugarController {
    @Autowired
    private BloodSugar bloodSugar;

    @PostMapping("/BloodSugar")
    public Result<String> BfzNode(){
        System.out.println("BloodSugar 执行");
        bloodSugar.bloodSugar();
        return Result.success("BloodSugar completed");
    }
}

