package com.course.controller;

import com.course.model.Result;
import com.course.service.BfzNote;
import com.course.service.BloodSugar;
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

