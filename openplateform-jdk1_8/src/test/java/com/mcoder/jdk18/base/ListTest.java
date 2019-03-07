package com.mcoder.jdk18.base;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liuheng
 * @Description: TODO
 * @date ${date} ${time}
 */
@Slf4j
public class ListTest {

    @Test
    public void loop() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(654);
        list.add(23);
        List<Integer> result = list.stream().filter(integer -> integer > 2)
                .map(integer -> integer + 5)
                .sorted(Integer::compareTo)
                .collect(Collectors.toList());
        log.info("loop:{}", result);
    }
}
