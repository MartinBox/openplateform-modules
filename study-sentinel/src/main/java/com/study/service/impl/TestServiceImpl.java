package com.study.service.impl;

import com.study.service.TestService;
import org.springframework.stereotype.Service;

/**
 * @author liuheng
 * @Description: TODO
 * @date ${date} ${time}
 */
@Service
public class TestServiceImpl implements TestService {
    @Override
    public String test() {
        return "hello world";
    }
}
