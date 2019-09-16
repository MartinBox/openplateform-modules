package com.mcoder.jdk18.base;

import com.mcoder.study.exception.Exceptional;
import com.mcoder.study.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author liuheng
 * @Description: TODO
 * @date 2019/3/13 14:56
 */
@Slf4j
public class ExceptionTest {

    @Test
    public void string() throws Exception {
        Exceptional<String> exceptional = Exceptional.from(() -> {
            if ("ddd3".length() > 3) {
                throw new NullPointerException();
            }
            return "ddd";
        });
        String name = TestService.name(exceptional);
        log.info(name);
    }

    @Test
    public void www() throws Exception {
        TestService.TestFuncInterface<String> testFuncInterface = () -> {
            //return "op9";
            return null;
        };

        String name = TestService.name2(Exceptional.from(() -> testFuncInterface)).test();
        log.info(name);
    }

    @Test
    public void www2() throws Exception {
        TestService.TestFuncInterface<Void> testFuncInterface = () -> {
            log.info("1+9=10");
            return null;
        };

        TestService.name2(Exceptional.from(() -> testFuncInterface)).test();
    }

    @Test
    public void www3() throws Exception {
        Exceptional<String> exceptional = Exceptional.from(() -> null);
        log.info(exceptional.get());
    }
}
