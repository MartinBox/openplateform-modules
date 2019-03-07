package com.openplatform.ftp;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.common.util.concurrent.RateLimiter;
import com.iboxpay.basepay.frame.net.Http;
import com.iboxpay.trade.front.data.entity.TermBaseEntity;
import com.iboxpay.trade.front.data.entity.TradeEntity;
import com.iboxpay.trade.front.data.model.PaymentRequsetModel;
import com.iboxpay.trade.front.data.service.openplfm.TermBaseService;
import com.iboxpay.trade.front.data.service.posp.TradeService;
import com.iboxpay.trade.front.server.TradeContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author liuheng
 * @Description: TODO
 * @date ${date} ${time}
 */
@RestController
@RequestMapping(value = "/factories")
public class TestController {
    private static Logger logger = LoggerFactory.getLogger(TestController.class);
    @Autowired
    private TradeService tradeService;

    @Autowired
    private TermBaseService termBaseService;
    @Autowired
    private TradeContext tradeContext;

    RateLimiter limiter = RateLimiter.create(150);

    @RequestMapping(path = "plus", method = RequestMethod.GET)
    public ResponseEntity query() {
        PaymentRequsetModel model = new PaymentRequsetModel();
        model.setDeviceSn("019920161028");
        model.setKeyVersion("1.0");

        TradeEntity tradeEntity = new TradeEntity();
        tradeEntity.setAuthCode("8889");

        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("order_no", "123");
        tradeService.update(tradeEntity, updateWrapper);
        return new ResponseEntity<Object>(200, HttpStatus.OK);
    }

    @RequestMapping(path = "context", method = RequestMethod.GET)
    public ResponseEntity context() {
        TermBaseEntity baseEntity = tradeContext.getCurrentTermInfo();
        return new ResponseEntity<Object>(200, HttpStatus.OK);
    }

    @RequestMapping(path = "limit", method = RequestMethod.GET)
    public ResponseEntity limit() {
        double acquire = limiter.acquire();
        logger.info("获取令牌" + acquire);
        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<Object>(200, HttpStatus.OK);
    }

    @RequestMapping(path = "okhttp", method = RequestMethod.GET)
    public ResponseEntity okhttp() throws IOException {
        String result = HttpHolder.getInstance().timeout(TimeUnit.SECONDS, 5)
                .url("http://172.30.60.178:7081/online/front/factories/limit")
                .get()
                .execute();
        logger.info("result:{}", result);
        /*logger.info("runningCallCount:{}", HttpHolder.getInstance().getOkHttpClient().getDispatcher().getRunningCallCount());
        logger.info("queuedCallCount:{}", HttpHolder.getInstance().getOkHttpClient().getDispatcher().getQueuedCallCount());*/
        return new ResponseEntity<Object>(200, HttpStatus.OK);
    }

    @RequestMapping(path = "okhttp2", method = RequestMethod.GET)
    public ResponseEntity okhttp2() throws IOException {
        String result = Http.anInstance(TimeUnit.SECONDS, 5)
                .url("http://172.30.60.178:7081/online/front/factories/limit")
                .get()
                .execute();
        logger.info("result:{}", result);
        return new ResponseEntity<Object>(200, HttpStatus.OK);
    }


    Semaphore semaphore = new Semaphore(50);

    @RequestMapping(path = "semaphore", method = RequestMethod.GET)
    public void Semaphore() throws InterruptedException {
        logger.info("start");
        long start = System.currentTimeMillis();
        /*if (semaphore.getQueueLength() > 20) {
            logger.info("refuse request,max queue:{}", semaphore.getQueueLength());
            return;
        }*/

        if (semaphore.tryAcquire(3, TimeUnit.SECONDS)) {
            try {
                logger.info("acquire allow,current queue length:{}, diff:{}", semaphore.getQueueLength(), System.currentTimeMillis() - start);
                // 处理核心逻辑
                TimeUnit.SECONDS.sleep(2);
                logger.info("exe biz ");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
            }
        } else {
            logger.info("acquire allow timeout " + 100);
        }
    }

    public static void main(String[] args) {
        System.out.println(TimeUnit.MICROSECONDS.toNanos(1020));

    }
}
