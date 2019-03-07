package com.study.ctrl;

import com.study.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuheng
 * @Description: TODO
 * @date ${date} ${time}
 */
@RestController
@RequestMapping
public class TestCtrl {
    @Autowired
    private TestService testService;
    @RequestMapping("test")
    public String test() {
        return testService.test();
    }

    @RequestMapping("fallback")
    public String fallback() {
        System.out.println("fallback");
        return "fallback";
    }
}
