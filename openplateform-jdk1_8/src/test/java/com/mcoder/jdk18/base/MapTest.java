package com.mcoder.jdk18.base;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.HashMap;

/**
 * @author liuheng
 * @Description: TODO
 * @date ${date} ${time}
 */
@Slf4j
public class MapTest {
    @Test
    public void merge() {
        java.util.Map<String, Integer> map = new HashMap<>();
        map.put("a", 3);
        map.put("b", 6);
        // 新增key
        map.merge("c", 77, (oldValue, value) -> 66);
        log.info("add ke:{}", map);
        // 更新key
        map.merge("b", 1, (oldValue, value) -> oldValue + value);
        log.info("update ke:{}", map);
        //  删除key
        map.merge("b", 1, (oldValue, value) -> null);
        log.info("delete ke:{}", map);
    }
}
