package com.mcoder.study;

import com.mcoder.study.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Unit test for simple App.
 */
public class UserTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void test() {
        UserService userService = Mockito.mock(UserService.class);
        String name = userService.name();
        Assert.assertEquals(name, "hello world");
    }
}
