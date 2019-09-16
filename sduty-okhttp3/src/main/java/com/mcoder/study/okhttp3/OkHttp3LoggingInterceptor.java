
package com.mcoder.study.okhttp3;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author liuheng
 * @Description: TODO
 * @date 2019/3/12 16:30
 */
@Slf4j
public class OkHttp3LoggingInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        StringBuilder params = new StringBuilder();
        try {
            RequestBody requestBody = request.body();
            BufferedSink bufferedRequestBody = new okio.Buffer();
            requestBody.writeTo(bufferedRequestBody);
            params.append(((okio.Buffer) bufferedRequestBody).readString(Charset.forName("utf-8")));
        } catch (IOException e) {
            log.warn("get request patams fail", e);
        }
        return chain.proceed(request);
    }

}
