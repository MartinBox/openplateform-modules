package com.mcoder.study.rest;

import com.mcoder.study.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author liuheng
 * @Description: TODO
 * @date 2019/3/11 15:47
 */
@RestController
@RequestMapping
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "/name")
    public String name() {
        return userService.name();
    }

    @PostMapping(value = "/login")
    public boolean login(@RequestBody Map<String, String> params) {
        return userService.login(params);
    }
}
