package com.mcoder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class JavaSocketClient {
    private static final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(30);

    public static void main(String[] args) throws IOException {
        IntStream.range(0, 10)
                .forEach(value -> fixedThreadPool.execute(() -> {
                    try {
                        send("172.30.60.227");
//                        send("127.0.0.1");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }));
    }

    private static void send(String ip) throws IOException {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        Socket socket = new Socket(ip, 8000);
        socket.setTcpNoDelay(true);
        socket.setKeepAlive(true);
        socket.setSoTimeout(300000);
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(uuid.getBytes(Charset.defaultCharset()));
        outputStream.flush();
        InputStream inputStream = socket.getInputStream();
        int length = inputStream.read();
        byte[] bytes = new byte[length];
        inputStream.read(bytes, 0, length);
        System.out.println(new String(bytes, Charset.defaultCharset()));
        System.out.println(format() + " " + socket.getLocalPort() + " " + uuid + " -> start close inputstream");
        inputStream.close();
        System.out.println(format() + " " + socket.getLocalPort() + " " + uuid + " -> start close outputStream");
        outputStream.close();


        /*outputStream.write("第二次发送请求".getBytes(Charset.defaultCharset()));
        outputStream.flush();
        length = inputStream.read();
        bytes = new byte[length];
        inputStream.read(bytes, 0, length);
        System.out.println(new String(bytes, Charset.defaultCharset()));*/
        System.out.println(format() + " " + socket.getLocalPort() + " " + uuid + " -> start close socket");
        socket.close();
    }

    private static String format() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS"));
    }
}
