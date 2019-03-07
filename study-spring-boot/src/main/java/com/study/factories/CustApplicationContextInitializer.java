package com.study.factories;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.ApplicationContextInitializer;

/**
 * @author liuheng
 * @Description: TODO
 * @date ${date} ${time}
 */
@Slf4j
public class CustApplicationContextInitializer implements ApplicationContextInitializer<AnnotationConfigServletWebServerApplicationContext> {
    @Override
    public void initialize(AnnotationConfigServletWebServerApplicationContext applicationContext) {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>initialize");
    }
}
