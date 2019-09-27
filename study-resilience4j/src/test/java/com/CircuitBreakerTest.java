package com;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.vavr.API;
import io.vavr.CheckedFunction0;
import io.vavr.Predicates;
import io.vavr.control.Try;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

/**
 * Unit factories for simple App.
 */

public class CircuitBreakerTest {
    private static Logger logger = LoggerFactory.getLogger(CircuitBreakerTest.class);

    /**
     * 使用默认熔断器配置
     */
    @Test
    public void params_gobal_default_test() {
        CircuitBreakerRegistry breakerRegistry = CircuitBreakerRegistry.ofDefaults();
        CircuitBreaker circuitBreaker = breakerRegistry.circuitBreaker("get_userinfo");
        CheckedFunction0<String> decorateCheckedSupplier = CircuitBreaker.decorateCheckedSupplier(circuitBreaker, () -> {
            return "hello,my name is ";
        });

        Try<String> stringTry = Try.of(decorateCheckedSupplier)
                .map(s -> s.concat(" lilei"));
        stringTry.isSuccess();
        System.out.println(stringTry.get());
    }

    /**
     * 自定义熔断器配置
     */
    @Test
    public void params_custom_test() {
        CircuitBreakerRegistry breakerRegistry = CircuitBreakerRegistry.ofDefaults();
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofSeconds(3))
                .ringBufferSizeInClosedState(3)
                .ringBufferSizeInHalfOpenState(3)
                .build();
        CircuitBreaker circuitBreaker = breakerRegistry.circuitBreaker("get_userinfo", circuitBreakerConfig);

        Try<String> result = Try.of(CircuitBreaker.decorateCheckedSupplier(circuitBreaker, () -> "123"))
                .recover(throwable -> "Hello from Recovery");
        logger.info("result:{}", result.get());

    }

    /**
     * 熔断器直接使用配置参数
     */
    @Test
    public void params_direct_test() {
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(70)
                .waitDurationInOpenState(Duration.ofSeconds(3))
                .ringBufferSizeInClosedState(3)
                .ringBufferSizeInHalfOpenState(3)
                .build();
        CircuitBreaker circuitBreaker = CircuitBreaker.of("get_userinfo", circuitBreakerConfig);
    }

    /**
     * 断路器->打开测试
     */
    @Test
    public void open_test() {
        CircuitBreakerRegistry breakerRegistry = CircuitBreakerRegistry.ofDefaults();
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofSeconds(3))
                .ringBufferSizeInClosedState(2)
                .ringBufferSizeInHalfOpenState(3)
                .build();
        CircuitBreaker circuitBreaker = breakerRegistry.circuitBreaker("get_userinfo", circuitBreakerConfig);
        // fail rate : 1 * 100.0f / 2(ringBufferSize) = ignore
        circuitBreaker.onError(0, new NullPointerException());
        //logger.info("circuit breaker state:{}", circuitBreaker.getState().getOrder());
        // fail rate : 2 * 100.0f / 2(ringBufferSize) = 100 > 50(failureRateThreshold)
        circuitBreaker.onError(0, new NullPointerException());
        // circuit breaker is open,throw exception
        Try<String> result = Try.of(CircuitBreaker.decorateCheckedSupplier(circuitBreaker, () -> "123"));
        // can't execute
        logger.info("result:{}", result.get());
    }

    /**
     * 断路器恢复
     */
    @Test
    public void recover_test() {
        int ringBufferSize = 2;
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(70)
                .waitDurationInOpenState(Duration.ofSeconds(3))
                .ringBufferSizeInClosedState(ringBufferSize)
                .ringBufferSizeInHalfOpenState(3)
                .build();
        CircuitBreaker circuitBreaker = CircuitBreaker.of("get_userinfo", circuitBreakerConfig);

        // 模拟断路器开启
        mock_breaker_rate(circuitBreaker, ringBufferSize);

        Try<String> result = Try.of(CircuitBreaker.decorateCheckedSupplier(circuitBreaker, () -> "123"))
                .recover(throwable -> "recover");
        System.out.println(result.get());

    }

    private void mock_breaker_rate(CircuitBreaker circuitBreaker, int count) {
        IntStream.range(0, count)
                .forEach(value -> circuitBreaker.onError(0, new NoSuchElementException()));
    }


    /**
     * The default exception handler counts all type of exceptions as failures and triggers the CircuitBreaker. If you want to use a custom exception handler, you have to implement the functional interface Predicate which has a method factories. The Predicate must return true if the exception should count as a failure, otherwise it must return false.
     * The following example shows how to ignore an IOException, but all other exception types still count as failures.
     */
    @Test
    public void cust_exception_handler_test() {
        int ringBufferSize = 2;
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(70)
                .waitDurationInOpenState(Duration.ofSeconds(3))
                .ringBufferSizeInClosedState(ringBufferSize)
                .ringBufferSizeInHalfOpenState(3)
                .recordFailure(throwable -> API.Match(throwable).of(
                        API.Case(API.$(Predicates.instanceOf(NullPointerException.class)), false),
                        API.Case(API.$(), true)))
                .build();
        CircuitBreaker circuitBreaker = CircuitBreaker.of("get_userinfo", circuitBreakerConfig);

        // 模拟断路器开启
        mock_breaker_rate(circuitBreaker, ringBufferSize - 1);

        Try<String> errorOpr = Try.of(CircuitBreaker.decorateCheckedSupplier(circuitBreaker, () -> {
            throw new NullPointerException();
        }));

        logger.info("result:{}", errorOpr.get());

    }

    /**
     * 消费发射事件
     */
    @Test
    public void register_event_test() {
        int ringBufferSize = 2;
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(70)
                .waitDurationInOpenState(Duration.ofSeconds(3))
                .ringBufferSizeInClosedState(ringBufferSize)
                .ringBufferSizeInHalfOpenState(3)
                //.ignoreExceptions(NoSuchElementException.class) // 忽略该异常
                .build();
        CircuitBreaker circuitBreaker = CircuitBreaker.of("get_userinfo", circuitBreakerConfig);
        circuitBreaker.getEventPublisher()
                .onSuccess(event -> {
                    logger.info("onSuccess event");
                })
                .onCallNotPermitted(event -> {
                    logger.info("onSuccess event");
                })
                .onError(event -> {
                    logger.info("onSuccess event:{}", event.getThrowable().getCause());
                })
                .onIgnoredError(event -> {
                    logger.info("onSuccess event");
                })
                .onReset(event -> {
                    logger.info("onSuccess event");
                });
        // 模拟断路器开启
        mock_breaker_rate(circuitBreaker, ringBufferSize - 1);

        Try<String> result = Try.of(CircuitBreaker.decorateCheckedSupplier(circuitBreaker, () -> "123"));
        logger.info("result:{}", result.get());
    }

    /*@Test
    public void listener_event_test() {
        int ringBufferSize = 3;
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(70)
                .waitDurationInOpenState(Duration.ofSeconds(3))
                .ringBufferSizeInClosedState(ringBufferSize)
                .ringBufferSizeInHalfOpenState(3)
                .build();

        // 声明事件队列
        CircularEventConsumer<CircuitBreakerEvent> circularEventConsumer = new CircularEventConsumer<>(50);
        CircuitBreaker circuitBreaker = CircuitBreaker.of("get_userinfo", circuitBreakerConfig);
        // 绑定事件队列
        circuitBreaker.getEventPublisher().onEvent(circularEventConsumer);
        // 模拟断路器开启
        mock_breaker_rate(circuitBreaker, ringBufferSize - 1);

        Try<String> result = Try.of(CircuitBreaker.decorateCheckedSupplier(circuitBreaker, () -> "123"));
        logger.info("result:{}", result.get());

        io.vavr.collection.List<CircuitBreakerEvent> bufferedEvents = circularEventConsumer.getBufferedEvents();
        bufferedEvents.toJavaList().forEach(circuitBreakerEvent -> {
            logger.info("circuitBreakerEvent Type Name :{}",circuitBreakerEvent.getEventType().name());
        });

    }*/
}
