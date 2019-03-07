package com.study.factories;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author liuheng
 * @Description: TODO
 * @date ${date} ${time}
 */
@Slf4j
public class CustApplicationListener implements ApplicationListener<ApplicationEvent> {
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> onApplicationEvent ->{}", event.toString());
    }
}
