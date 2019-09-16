package com.mcoder.study.okhttp3;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author liuheng
 * @Description: TODO
 * @date 2019/3/13 10:58
 */
@Slf4j
public class Http {
    protected static final String DEFAULT_CHARSET = "utf-8";

    protected String url;
    protected StringBuilder urlParams;

    private Request.Builder builder;
    private RequestBody requestBody;
    protected String method;
    private WebSocketListener webSocketListener;
    private OkHttpClient okHttpClient;

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

    public Http() {
        builder = new Request.Builder();
    }

    public static Http anInstance() {
        return new Http();
    }

    public Http defaultHttpClient() {
        this.okHttpClient = defaultOkHttpClient;
        return this;
    }


    public Http httpClient(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
        return this;
    }

    public Http url(String url) {
        this.url = url;
        return this;
    }

    public Http headers(Headers headers) {
        builder.headers(headers);
        return this;
    }

    public Http header(String key, String value) {
        builder.header(key, value);
        return this;
    }

    public Http addWebSocketListener(WebSocketListener webSocketListener) {
        this.webSocketListener = webSocketListener;
        return this;
    }

    public Http urlParams(Map<String, String> urlParamMap) throws UnsupportedEncodingException {
        return urlParams(urlParamMap, DEFAULT_CHARSET, true);
    }

    public Http urlParams(Map<String, String> urlParamMap, String charset) throws UnsupportedEncodingException {
        return urlParams(urlParamMap, charset, true);
    }

    public Http urlParams(Map<String, String> urlParamMap, boolean isEncoding) throws UnsupportedEncodingException {
        return urlParams(urlParamMap, DEFAULT_CHARSET, isEncoding);
    }

    public Http urlParams(Map<String, String> urlParamMap, String charset, boolean isEncoding) throws UnsupportedEncodingException {
        if (urlParamMap != null && !urlParamMap.isEmpty()) {
            urlParams = new StringBuilder();
            for (Map.Entry<String, String> e : urlParamMap.entrySet()) {
                urlParams.append(String.format("%s=%s&", e.getKey(), isEncoding ? URLEncoder.encode(e.getValue(), charset) : e.getValue()));
            }
        }
        return this;
    }

    public Http xmlBody(String text) {
        return body(MediaType.parse("application/xml; charset=utf-8"), text);
    }

    public Http jacksonBody(String text) {
        return body(MediaType.parse("application/json; charset=utf-8"), text);
    }

    public Http formBody(Map<String, String> formMap) {
        if (formMap != null && !formMap.isEmpty()) {
            FormBody.Builder formBuilder = new FormBody.Builder();
            for (Map.Entry<String, String> entry : formMap.entrySet()) {
                formBuilder.add(entry.getKey(), StringUtils.isNotBlank(entry.getValue()) ? entry.getValue() : "");
            }
            requestBody = formBuilder.build();
        }
        return this;
    }

    public Http formBodyWithFile(Map<String, String> formMap, byte[] file, String fileName, String mapName) {
        if (formMap != null && !formMap.isEmpty()) {
            MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder();
            multipartBodyBuilder.setType(MultipartBody.FORM);
            for (Map.Entry<String, String> entry : formMap.entrySet()) {
                multipartBodyBuilder.addFormDataPart(entry.getKey(), entry.getValue());
            }
            multipartBodyBuilder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + mapName + "\";filename=\"" + fileName + "\"", "Content-Transfer-Encoding", "binary"),
                    RequestBody.create(MediaType.parse("application/octet-stream"), file));
            this.requestBody = multipartBodyBuilder.build();
        }
        return this;
    }

    private void prepare() {
        if (StringUtils.isNotBlank(urlParams)) {
            url = String.format("%s?%s", url, urlParams.toString().replaceAll("&$", ""));
        }
        /*builder.url(url).method(method, method.equals(HttpMethod.POST) ? body : null);*/
        builder.url(url)
                .method(method, okhttp3.internal.http.HttpMethod.requiresRequestBody(method) ? requestBody : null);
    }

    public Http body(MediaType type, String text) {
        requestBody = RequestBody.create(type, text);
        return this;
    }

    public Http post() {
        this.method = HttpMethod.POST;
        return this;
    }

    public Http put() {
        this.method = HttpMethod.PUT;
        return this;
    }

    public Http get() {
        this.method = HttpMethod.GET;
        return this;
    }

    public String execute() throws IOException {
        prepare();
        Request request = builder.build();
        if (webSocketListener != null) {
            okHttpClient.newWebSocket(request, webSocketListener);
        }
        Response response = okHttpClient.newCall(request).execute();
        if (response != null) {
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("request [").append(url).append("]")
                        .append(" fail. ")
                        .append("code -> ").append(response.code())
                        .append(" . ")
                        .append("message-> ").append(response.message());
                throw new IOException(stringBuilder.toString());
            }
        }
        return null;
    }

    public InputStream executeStream() throws IOException {
        prepare();
        Response response = okHttpClient.newCall(builder.build()).execute();
        if (response != null) {
            if (response.isSuccessful()) {
                return response.body().byteStream();
            } else {
                log.info("request is failure, code:{} message:{}", response.code(), response.message());
            }
        }
        return null;
    }

    interface HttpMethod {

        String POST = "POST";

        String GET = "GET";

        String PUT = "PUT";

    }
}
