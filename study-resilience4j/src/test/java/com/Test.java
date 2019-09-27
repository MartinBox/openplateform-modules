package com;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.vavr.control.Try;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class Test {
    static CircuitBreakerRegistry breakerRegistry = CircuitBreakerRegistry.ofDefaults();

    public static void main(String[] args) {
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                // 默认故障率阈值，百分比
                .failureRateThreshold(90)
                // 最小调用次数
                .minimumNumberOfCalls(10000)
                // 半开状态下允许的调用次数
                .permittedNumberOfCallsInHalfOpenState(10)
                // 滑动窗尺寸
                .slidingWindowSize(100)
                // 滑动窗类型
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                // 在开放状态下等待时间。单位：秒
                .waitDurationInOpenState(Duration.ofSeconds(60))
                // 自动从打开过渡到半开
                .automaticTransitionFromOpenToHalfOpenEnabled(false)
                // 慢调用率阈值
                .slowCallRateThreshold(10)
                // 慢调用持续时间阈值
                .slowCallDurationThreshold(Duration.ofMillis(300))

                .build();
        CircuitBreaker circuitBreaker = breakerRegistry.circuitBreaker("mcoder.test4", circuitBreakerConfig);

        String uuid = UUID.randomUUID().toString();
        Supplier<String> decoratedSupplier = CircuitBreaker.decorateSupplier(circuitBreaker, () -> {
            try {
                double rate = Math.random();
                System.out.println(uuid + " " + rate);
                if (rate > 0.7) {
                    System.out.println(uuid + " exception");
                    throw new NullPointerException("");
                }
                TimeUnit.MILLISECONDS.sleep(rate > 0.7 ? 60 : 70);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(uuid + " success");
            return "success";
        });
        Try<String> ofSupplier = Try.ofSupplier(decoratedSupplier);
        Try<String> recover = ofSupplier.recover(throwable -> {
            System.out.println(uuid + " recover");
            return "recover";
        });
        String result = recover.get();
    }

}
