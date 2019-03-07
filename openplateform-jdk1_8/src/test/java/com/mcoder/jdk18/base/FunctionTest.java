package com.mcoder.jdk18.base;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author liuheng
 * @Description: jdk1.8内置Function测试
 * @date 2018/11/25 17:43
 */
@Slf4j
public class FunctionTest {

    public String biFunction(String value1, String value2, BiFunction<String, String, String> function) {
        return function.apply(value1, value2);
    }

    public String biFunctionAfter(String value1, String value2, BiFunction<String, String, String> function, Function<String, String> functionAfter) {
        return function.andThen(functionAfter).apply(value1, value2);
    }

    public String function(String value, Function<String, String> function) {
        return function.apply(value);
    }

    public String functionBefore(String value, Function<String, String> function, Function<String, String> functionBefore) {
        return function.compose(functionBefore).apply(value);
    }

    /**
     * @see BiFunction 给定两个个参数，返回一个值
     */
    @Test
    public void biFunction() {
        log.info("biFunction:{}", biFunction("a", "b", (value1, value2) -> value1 + value2));
    }

    @Test
    public void biFunctionAfter() {
        log.info("biFunction after:{}", biFunctionAfter("a", "b", (value1, value2) -> value1 + value2, value -> value + " after output."));
    }

    /**
     * @see Function 给定一个参数，返回一个值
     */
    @Test
    public void function() {
        log.info("function:{}", function("a", value -> value + " is single param"));
    }

    @Test
    public void functionBefore() {
        log.info("function before:{}", functionBefore("a", value -> value + " after input.", value -> value + " before output."));
    }
}
