package com.mcoder.study.okhttp3;

import okhttp3.ConnectionPool;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.TlsVersion;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

/**
 * @author liuheng
 * @Description: TODO
 * @date 2019/7/24 17:25
 */
public class OkHttpClientHolder {
    protected static final TimeUnit DEFAULT_TIMEUNIT = TimeUnit.SECONDS;
    protected static final int DEFAULT_TIMEOUT = 60;
    public static final HostnameVerifier DEFAULT_HOST_VERIFIER = (s, sslSession) -> true;
    protected OkHttpClient.Builder okHttpClientBuilder;
    private boolean retryOnConnectionFailure = false;

    public static OkHttpClientHolder anInstance() {
        return new OkHttpClientHolder(DEFAULT_TIMEUNIT, DEFAULT_TIMEOUT);
    }

    public static OkHttpClientHolder anInstance(TimeUnit timeUnit, int timeout) {
        return new OkHttpClientHolder(timeUnit, timeout);
    }

    public OkHttpClientHolder trustAnyServer() {
        okHttpClientBuilder.hostnameVerifier(DEFAULT_HOST_VERIFIER);
        return this;
    }

    public OkHttpClientHolder(TimeUnit timeUnit, int timeout) {
        okHttpClientBuilder = new OkHttpClient.Builder()
                .readTimeout(timeout, timeUnit)
                .connectTimeout(timeout, timeUnit)
                .writeTimeout(timeout, timeUnit);
    }

    public OkHttpClientHolder addInterceptor(Interceptor interceptor) {
        okHttpClientBuilder.addInterceptor(interceptor);
        return this;
    }

    public OkHttpClientHolder addNetworkInterceptor(Interceptor interceptor) {
        okHttpClientBuilder.addNetworkInterceptor(interceptor);
        return this;
    }

    public OkHttpClientHolder retryOnConnectionFailure(boolean retryOnConnectionFailure) {
        this.retryOnConnectionFailure = retryOnConnectionFailure;
        return this;
    }

    public OkHttpClientHolder connectionPool(ConnectionPool connectionPool){
        okHttpClientBuilder.connectionPool(connectionPool);
        return this;
    }

    public OkHttpClient buildHttpClient() {
        return okHttpClientBuilder.retryOnConnectionFailure(retryOnConnectionFailure)
                .build();
    }

    public OkHttpClientHolder trustAllHostnameVerifier() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext ctx = SSLContext.getInstance(TlsVersion.TLS_1_2.javaName());
        X509TrustManager x509TrustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
        okHttpClientBuilder.hostnameVerifier(DEFAULT_HOST_VERIFIER);
        ctx.init(null, new TrustManager[]{x509TrustManager}, null);
        okHttpClientBuilder.sslSocketFactory(ctx.getSocketFactory(), x509TrustManager);
        return this;
    }

}
