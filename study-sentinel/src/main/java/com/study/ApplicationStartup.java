package com.study;

import com.alibaba.csp.sentinel.adapter.servlet.callback.WebCallbackManager;
import com.study.ctrl.CustomUrlBlockHandler;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @author liuheng
 * @Description: 监听进程启动
 * @date 2018/8/1 15:12
 */
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        // 自定义降级处理
        WebCallbackManager.setUrlBlockHandler(new CustomUrlBlockHandler());
    }
}
