package com.watermelon.beatckc.controller;

import com.watermelon.beatckc.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class ResultController {
    @GetMapping
    public Result getResult(){
        return Result.success();
    }
}
