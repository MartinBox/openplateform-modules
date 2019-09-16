package com.mcoder.study;

import com.mcoder.study.okhttp3.Http;
import com.mcoder.study.okhttp3.OkHttpClientHolder;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.internal.Util;
import org.junit.Test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author liuheng
 * @Description: TODO
 * @date 2019/3/13 11:12
 */
@Slf4j
public class HttpTest {
    private static final Executor executor = new ThreadPoolExecutor(0 /* corePoolSize */,
            Integer.MAX_VALUE /* maximumPoolSize */, 60L /* keepAliveTime */, TimeUnit.SECONDS,
            new SynchronousQueue<>(), Util.threadFactory("OkHttp ConnectionPool", true));

    /**
     * 默认OkHttpClient实例
     */
    private static OkHttpClient defaultOkHttpClient;

    static {
        try {
            defaultOkHttpClient = OkHttpClientHolder.anInstance(TimeUnit.SECONDS, 5)
                    .trustAllHostnameVerifier()
                    .retryOnConnectionFailure(false)
                    .buildHttpClient();
        } catch (Exception e) {
            log.error("http instance init default httpclient fail", e);
        }
    }

    String baidu = "https://www.baidu.com";

    @Test
    public void get() throws IOException {
        String result = Http.anInstance()
                .defaultHttpClient()
                .url(baidu)
                .get()
                .execute();
        log.info(result);
    }

    @Test
    public void websocket() throws IOException {
        String result = Http.anInstance()
                .defaultHttpClient()
                .url("http://127.0.0.1:80/messages/1/2/3")
                .get()
                .execute();
        log.info(result);
    }

    @Test
    public void post() throws IOException, InterruptedException {
        int count = 50;
        CountDownLatch downLatch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            executor.execute(() -> {
                try {
                    send();
                    downLatch.countDown();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        downLatch.await();
        System.out.println("end");
    }

    @Test
    public void send() throws IOException {
        String url = "http://172.30.23.68:8080/unionadp/merchant/query";
        String str = "{\"merId\":\"11\",\"acqSpId\":\"S20190416160947CHZZF\",\"signature\":\"Ia8ksUkpdMBpndnZrmdNh1otmsdyJxy/gZ/5fqwKI9f4B8tjCmzOmo5VNZtW4LdZ29/ZoqqHoUixqAOLo/HkYMPvFssiQ1ly/gTyuf2QgIYlfgxk8yAsI7jFnUQi+t8FqmAr4sAw5IrCzAU+9k1HnrrWf10ZUfU39MCCA6cVHjo=\",\"acqMerId\":\"015440309121086\",\"acqOrgCode\":\"48130000\"}";
        String result = Http.anInstance()
                .httpClient(defaultOkHttpClient)
                .url(url)
                .header("connection", "keep-alive")
                .jacksonBody(str)
                .post()
                .execute();
        System.out.println(result);
    }

    @Test
    public void xxx() throws IOException, InterruptedException {
        String url = "http://172.30.23.68:8080/unionadp/merchant/query";
        //url = "http://127.0.0.1:8082/unionadp/test/1";
        String str = "{\"merId\":\"M20190830141731C6811\",\"acqSpId\":\"S20190416160947CHZZF\",\"signature\":\"Ia8ksUkpdMBpndnZrmdNh1otmsdyJxy/gZ/5fqwKI9f4B8tjCmzOmo5VNZtW4LdZ29/ZoqqHoUixqAOLo/HkYMPvFssiQ1ly/gTyuf2QgIYlfgxk8yAsI7jFnUQi+t8FqmAr4sAw5IrCzAU+9k1HnrrWf10ZUfU39MCCA6cVHjo=\",\"acqMerId\":\"015440309121086\",\"acqOrgCode\":\"48130000\"}";
        HttpURLConnection connection = null;
        OutputStream out = null;
        InputStream in = null;
        InputStream in2 = null;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestProperty("Content-type", "application/json");
           /* connection.setRequestProperty("connection", "keep-alive");*/
            connection.setDoInput(true);
            connection.setDoOutput(true);
            out = connection.getOutputStream();
            out.close();

            out.write(str.getBytes());
            in = connection.getInputStream();
            System.out.println(read(connection, in));
      /*      System.out.println("**********************************************");
            //TimeUnit.SECONDS.sleep(5);
            out.write(str.getBytes());
            in2 = connection.getInputStream();
            System.out.println(read(connection, in2));*/

        } finally {
            if (out != null) out.close();
            if (in != null) in.close();
            if (connection != null) connection.disconnect();

        }
    }


    private String read(HttpURLConnection connection, InputStream in) throws IOException {
        Map<String, List<String>> headers = connection.getHeaderFields();
        if (null != headers) {
            headers.forEach((key, list) -> System.out.println(String.format("%-20s: %-50s", key, list)));
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        StringBuilder builder = new StringBuilder();
        String line = "";
        while ((line = bufferedReader.readLine()) != null) {
            builder.append(line);
        }
        return builder.toString();
    }
}
