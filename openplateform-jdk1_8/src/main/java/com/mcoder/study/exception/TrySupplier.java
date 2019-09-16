package com.mcoder.study.exception;

/**
 * @author liuheng
 * @Description: TODO
 * @date 2019/3/13 14:44
 */
@FunctionalInterface
public interface TrySupplier<T> {
    T tryGet() throws Throwable;
}