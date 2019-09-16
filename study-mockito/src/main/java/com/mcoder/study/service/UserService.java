package com.mcoder.study.service;

import java.util.Map;

/**
 * @author liuheng
 * @Description: TODO
 * @date ${date} ${time}
 */
public interface UserService {
    String name();

    boolean login(Map<String, String> params);
}
