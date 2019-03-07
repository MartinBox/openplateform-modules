package com.study.factories;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author liuheng
 * @Description: TODO
 * @date ${date} ${time}
 */
@Slf4j
public class CustSpringApplicationRunListener implements SpringApplicationRunListener {

    private final SpringApplication application;

    private final String[] args;

    private final SimpleApplicationEventMulticaster initialMulticaster;

    public CustSpringApplicationRunListener(SpringApplication application, String[] args) {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> CustSpringApplicationRunListener Constructor");
        this.application = application;
        this.args = args;
        this.initialMulticaster = new SimpleApplicationEventMulticaster();
        for (ApplicationListener<?> listener : application.getListeners()) {
            this.initialMulticaster.addApplicationListener(listener);
        }
    }

    @Override
    public void starting() {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>starting");
    }

    @Override
    public void started(ConfigurableApplicationContext context) {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>started");
    }

    @Override
    public void running(ConfigurableApplicationContext context) {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>running");
    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>failed");
    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>environmentPrepared");
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>contextPrepared");
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>contextLoaded");
    }

}
