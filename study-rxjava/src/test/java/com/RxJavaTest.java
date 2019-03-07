package com;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Unit factories for simple App.
 */
public class RxJavaTest {
    private Logger logger = LoggerFactory.getLogger(RxJavaTest.class);
    Observable observable = Observable.create(observableEmitter -> {
        logger.info("Observable thread is : " + Thread.currentThread().getName());
        observableEmitter.onNext("a");
        logger.info(" send a");
        observableEmitter.onNext("b");
        logger.info(" send b");
        observableEmitter.onNext("c");
        logger.info(" send c");
        observableEmitter.onNext("d");
        logger.info(" send d");
          /*  if ("11".startsWith("1")) {
                throw new NullPointerException();
            }*/
        observableEmitter.onComplete();
    });

    @Test
    public void observer() {
        Observer observer = new Observer() {
            @Override
            public void onSubscribe(Disposable disposable) {
                logger.info(" onSubscribe");
            }

            @Override
            public void onNext(Object o) {
                logger.info(" onNext:" + o);
            }

            @Override
            public void onError(Throwable throwable) {
                logger.info(" onError");
            }

            @Override
            public void onComplete() {
                logger.info(" onComplete");
            }
        };

        observable.subscribeOn(Schedulers.io()).subscribe(observer);
    }


    @Test
    public void functionSubscribeOn() {

        observable.subscribeOn(Schedulers.io())
                .doOnNext(o -> logger.info(" function onNext:" + o))
                .doOnError(throwable -> logger.info(" function onError:" + throwable))
                .doOnComplete(() -> logger.info(" function onComplete"))
                .subscribe(o -> logger.info(" function subscribe:" + o));
    }

    @Test
    public void threadPool() {

        observable.subscribeOn(Schedulers.newThread()) // 对发射线程进行切换
                .observeOn(Schedulers.io()) // 接收线程的切换
                .doOnNext(o -> logger.info(" function onNext:" + o))
                .doOnError(throwable -> logger.info(" function onError:" + throwable))
                .doOnComplete(() -> logger.info(" function onComplete"))
                .subscribe(o -> logger.info(" function subscribe:" + o));

    }


    /**
     * map 操作符可以将一个 Observable 对象通过某种关系转换为另一个Observable 对象。
     */
    @Test
    public void mapTest() {
        Observable<Response> observable = Observable.create(observableEmitter -> {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(1, TimeUnit.SECONDS)
                    .readTimeout(1, TimeUnit.SECONDS)
                    .writeTimeout(1, TimeUnit.SECONDS)
                    .build();
            Request request = new Request.Builder()
                    .url("http://127.0.0.1:7081/online/front/param/download/v1")
                    .post(RequestBody.create(MediaType.parse("application/json"), "{}"))
                    .build();
            logger.info(" start network request");
            Response response = okHttpClient.newCall(request).execute();
            observableEmitter.onNext(response);
        });
        observable.map(response -> response.body().string())
                .observeOn(Schedulers.io())
                .doOnError(throwable -> logger.info(" function onError:" + throwable))
                .doOnComplete(() -> logger.info(" function onComplete"))
                .subscribe(response -> {
                    logger.info(" function subscribe:" + response);
                });
    }

    /**
     * concat 可以做到不交错的发射两个甚至多个 Observable 的发射事件，
     * 并且只有前一个 Observable 终止(onComplete) 后才会订阅下一个 Observable
     * 例子：先查询缓存，缓存不存在，则执行远程查找
     */
    @Test
    public void concatTest() {
        Observable<String> cacheObservable = Observable.create(observableEmitter -> {
            if (false) {
                observableEmitter.onNext("find from cache");
            } else {
                observableEmitter.onComplete();
            }
        });
        Observable<String> remoteObservable = Observable.create(observableEmitter -> {
            observableEmitter.onNext("find from remote");
        });

        Observable.concat(cacheObservable, remoteObservable)
                .observeOn(Schedulers.io())
                .subscribe(value -> logger.info(value));
    }

    /**
     * flatMap 操作符可以将一个发射数据的 Observable 变换为多个 Observables ，
     * 然后将它们发射的数据合并后放到一个单独的 Observable
     * <p>
     * 例子：用户注册成功后需要自动登录，我们只需要先通过注册接口注册用户信息，注册成功后马上调用登录接口进行自动登录即可
     *
     * @throws InterruptedException
     */
    @Test
    public void flatMapTest() throws InterruptedException {
        // 等待所有异步操作执行完毕后，再退出jvm
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Observable.create(observableEmitter -> {
            logger.info("start send ");
            observableEmitter.onNext("register user info success");
        }).subscribeOn(Schedulers.io()) // 在IO线程中进行 “注册”
                .observeOn(Schedulers.newThread()) // 创建新线程获取 “注册”的结果
                .doOnNext(userInfo -> {
                    logger.info(" user info: " + userInfo);
                })
                .observeOn(Schedulers.io())  // 回到 io 线程去处理“登录”
                .flatMap(userInfo -> {
                    logger.info("execute flatMap, start login");
                    return Observable.just(userInfo + " -> login success");
                })
                .observeOn(Schedulers.io())
                .subscribe(o -> {
                    logger.info(" subscribe info: " + o);
                    countDownLatch.countDown();
                });
        logger.info(" waitting ");
        countDownLatch.await();
        logger.info(" end ");
    }


    @Test
    public void zipTest() throws InterruptedException {
        // 等待所有异步操作执行完毕后，再退出jvm
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Observable<String> observable1 = Observable.create(observableEmitter -> {
            logger.info("observable1 start send");
            observableEmitter.onNext("find from cache");
        });
        Observable<Integer> observable2 = Observable.create(observableEmitter -> {
            logger.info("observable2 start send");
            observableEmitter.onNext(123);
        });

        Observable.zip(observable1, observable2, (value1, value2) -> {
            return value1 + " -> " + value2;
        }).subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .subscribe(result -> {
                    logger.info("result:{}", result);
                    countDownLatch.countDown();
                });
        logger.info(" waitting ");
        countDownLatch.await();
        logger.info(" end ");
    }
}
