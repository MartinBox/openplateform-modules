package com.openplateform.spi.jdk.svc.impl;

import com.openplateform.spi.jdk.svc.TestService;

/**
 * @author liuheng
 * @Description: TODO
 * @date ${date} ${time}
 */
public class TestServiceImpl implements TestService {
    @Override
    public String say() {
        return "TestServiceImpl";
    }
}
