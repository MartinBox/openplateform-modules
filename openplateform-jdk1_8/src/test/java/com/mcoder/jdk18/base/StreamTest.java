package com.mcoder.jdk18.base;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * @author liuheng
 * @Description: TODO
 * @date 2018/11/25 18:02
 */
@Slf4j
public class StreamTest {
    /**
     * 等同写法;for (int i = startInclusive; i < endInclusive ; i++) { ... }
     * <p>
     * 1 不同于 for，range 不会强迫我们初始化某个可变变量。
     * 2 迭代会自动执行，所以我们不需要像循环索引一样定义增量。
     */
    @Test
    public void intStream_range() {
        IntStream.range(1, 20)
                .forEach(value -> log.info("intstream:{}", value));
    }

    /**
     * 等同写法：for (int i = startInclusive; i <= endInclusive ; i++) { ... }
     */
    @Test
    public void intStream_range_close() {
        IntStream.rangeClosed(1, 20)
                .forEach(value -> log.info("intstream:{}", value));
    }

    /**
     * 参数 value 在每次迭代中都表现为一个全新的变量。它是实际最终变量，因为我们不会在任何地方更改它的值
     */
    @Test
    public void intStream_range_final() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        IntStream.range(0, 5)
                .forEach(value ->
                        executorService.submit(() -> System.out.println("intstream task " + value)));
        executorService.shutdown();
    }


}
