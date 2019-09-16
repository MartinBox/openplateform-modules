package com.mcoder.study.service;

import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author liuheng
 * @Description: TODO
 * @date 2019/3/11 15:47
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public String name() {
        return "hello world";
    }

    @Override
    public boolean login(Map<String, String> params) {
        System.out.println(params.toString());
        return false;
    }
}
