package com.mcoder.study.service;

import com.mcoder.study.exception.Exceptional;

/**
 * @author liuheng
 * @Description: TODO
 * @date 2019/3/13 14:56
 */
public class TestService {

    public static String name(Exceptional<String> exceptional) throws Exception {
        if ("ddd".equals(exceptional.get())) {
            throw new IllegalArgumentException();
        }
        return exceptional.get() + " -> lilei";
    }

    public static <R> R name2(Exceptional<R> exceptional) throws Exception {
        if ("ddd".equals(exceptional.get())) {
            throw new IllegalArgumentException();
        }
        R result = exceptional.get();
        return result;
    }

    @FunctionalInterface
    public interface TestFuncInterface<R> {
        R test() throws Exception;
    }
}
