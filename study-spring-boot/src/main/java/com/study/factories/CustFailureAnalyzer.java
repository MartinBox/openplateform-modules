package com.study.factories;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.diagnostics.FailureAnalysis;
import org.springframework.boot.diagnostics.FailureAnalyzer;

/**
 * @author liuheng
 * @Description: TODO
 * @date ${date} ${time}
 */
@Slf4j
public class CustFailureAnalyzer implements FailureAnalyzer {
    @Override
    public FailureAnalysis analyze(Throwable failure) {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>analyze");
        return null;
    }
}
