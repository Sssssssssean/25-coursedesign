package com.course.controller;
import com.course.model.Result;
import com.course.service.BfzNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Component;

/**
 * @author lixuy
 * Created on 2019-04-11
 */
@Component
//填写并发症记录
@RestController
public class BfzNoteController {
    @Autowired
    private BfzNote bfzNote;

    @PostMapping("/bfzNode")
    public Result<String> BfzNode(){
        System.out.println("======被拦截的bfzNote方法执行======");
        bfzNote.BfzNode();
        return Result.success("BfzNote completed");
    }
}