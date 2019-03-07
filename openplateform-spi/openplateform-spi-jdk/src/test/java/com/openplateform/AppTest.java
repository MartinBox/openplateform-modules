package com.openplateform;

import com.openplateform.spi.jdk.ExtensionLoader;
import com.openplateform.spi.jdk.svc.TestService;
import org.junit.Test;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.regex.Pattern;

/**
 * Unit factories for simple App.
 */
public class AppTest {
    private static final Pattern NAME_SEPARATOR = Pattern.compile("\\s*[,]+\\s*");

    /**
     * Rigorous Test :-)
     */
    @Test
    public void findSubClassImpl() {
        ServiceLoader<TestService> serviceLoader = ServiceLoader.load(TestService.class);
        Iterator<TestService> serviceIterator = serviceLoader.iterator();
        while (serviceIterator.hasNext()) {
            System.out.println(serviceIterator.next().say());
        }
        System.out.println("end");
    }

    @Test
    public void findAllService() throws IOException {
        Map<String, Class<?>> classMap = ExtensionLoader.findAllExtensionMap();
        classMap.forEach((s, aClass) -> System.out.println(s + " : " + aClass));
    }

}
