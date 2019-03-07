package com;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.vavr.CheckedFunction0;
import io.vavr.CheckedRunnable;
import io.vavr.control.Try;
import org.junit.Test;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * Unit factories for simple App.
 */
public class CircuitBreakerTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void resilience() {
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofMillis(1000))
                .ringBufferSizeInHalfOpenState(20)
                .ringBufferSizeInClosedState(2)
                .build();
        // Create a CircuitBreakerRegistry with a custom global configuration
        CircuitBreakerRegistry circuitBreakerRegistry = CircuitBreakerRegistry.of(circuitBreakerConfig);

        // Get a CircuitBreaker from the CircuitBreakerRegistry with the global default configuration
        CircuitBreaker circuitBreaker2 = circuitBreakerRegistry.circuitBreaker("otherName");

        // Get a CircuitBreaker from the CircuitBreakerRegistry with a custom configuration
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("uniqueName", circuitBreakerConfig);

        // Given
        //CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("testName");

        // When I decorate my function
        CheckedFunction0<String> decoratedSupplier = CircuitBreaker
                .decorateCheckedSupplier(circuitBreaker, () -> "This can be any method which returns: 'Hello");

        CheckedRunnable runnableChecked = CircuitBreaker.decorateCheckedRunnable(circuitBreaker, () -> {
            TimeUnit.SECONDS.sleep(3);
            System.out.println("hello world");
        });
        // and chain an other function with map
        Try<String> result = Try.of(decoratedSupplier)
                .map(value -> value + " world'");

        if (result.isSuccess()) {
            System.out.println(result.get());
        }
        Try runRsp = Try.run(runnableChecked);
        Try<String> runRsp2 = Try.run(runnableChecked)
                .map(value -> {
                    /*try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                    return value + "run";
                });

        IntStream.range(0, 100)
                .forEach(value -> {
                    System.out.println("do " + value + " " + runRsp.get());
                });
       /* if (runRsp.isSuccess()) {
            System.out.println(runRsp.get());
        }*/


       /* // Then the Try Monad returns a Success<String>, if all functions ran successfully.
        assertThat(result.isSuccess()).isTrue();
        assertThat(result.get()).isEqualTo("This can be any method which returns: 'Hello world'");*/
    }
}
