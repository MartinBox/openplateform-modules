package com.openplateform.spi.jdk;

import com.openplateform.spi.jdk.svc.TestService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author liuheng
 * @Description: TODO
 * @date ${date} ${time}
 */
public class ExtensionLoader {

    public static Map<String, Class<?>> findAllExtensionMap() throws IOException {
        ClassLoader classLoader = ExtensionLoader.class.getClassLoader();
        Enumeration<URL> urls = classLoader.getResources("META-INF/services/" + TestService.class.getName());
        if (urls != null) {
            Map<String, Class<?>> extensionClasses = new HashMap<>();
            while (urls.hasMoreElements()) {
                java.net.URL resourceURL = urls.nextElement();
                loadResource(extensionClasses, classLoader, resourceURL);
            }
            return extensionClasses;
        }
        return Collections.EMPTY_MAP;
    }

    public static List<Class<?>> findAllExtension() throws IOException {
        Map<String, Class<?>> extensionClasses = findAllExtensionMap();
        return extensionClasses.entrySet().stream().map(m -> m.getValue()).collect(Collectors.toList());
    }

    public static void loadResource(Map<String, Class<?>> extensionClasses, ClassLoader classLoader, java.net.URL resourceURL) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(resourceURL.openStream(), "utf-8"));
            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("#")) {
                        continue;
                    }
                    int i = line.indexOf('=');
                    String name = null;
                    if (i > 0) {
                        name = line.substring(0, i).trim();
                        line = line.substring(i + 1).trim();
                    } else {
                        line = line.trim();
                    }

                    if (line.length() > 0) {
                        try {
                            Class clazz = Class.forName(line, true, classLoader);
                            loadClass(extensionClasses, resourceURL, clazz, name == null ? clazz.getSimpleName() : name);
                        } catch (Throwable t) {
                            t.printStackTrace();
                        }
                    }
                }
            } finally {
                reader.close();
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }


    public static void loadClass(Map<String, Class<?>> extensionClasses, java.net.URL resourceURL, Class<?> clazz, String name) throws NoSuchMethodException {
        clazz.getConstructor();
        Class<?> c = extensionClasses.get(name);
        if (c == null) {
            extensionClasses.put(name, clazz);
        }
    }
}
