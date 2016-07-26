package rxTest;

import junit.framework.Assert;

import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import base.TestInit;
import rx.functions.Action1;
import rx.subjects.PublishSubject;

/**
 * debounce的作用是：任意两次请求的时间间隔只要是小于给定的阀值，前一次就会被丢弃。
 * 比如给定的阀值是1秒，那么如果有1000次请求，每两次请求之前的间隔都小于1秒，那么只会把最后一次请求发送出去
 * Created by cb on 2016/7/26.
 */
public class testDebounce extends TestInit {
    @Test
    public void testDebounce() throws InterruptedException {
        final ArrayList<Long> accept = new ArrayList<>();
        final PublishSubject<Long> subject = PublishSubject.create();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (Long i = 0L;i<10L;i++){
                    try {
                        //开启一个线程，每200毫秒发一次onNext
                        Thread.sleep(200);
                        System.out.print("onNext:" + i + "\n");
                        subject.onNext(i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        subject.debounce(1,TimeUnit.SECONDS).subscribe(new Action1<Long>() {
            @Override
            public void call(final Long aLong) {
                //这里把实际被丢过来的数字存起来
                accept.add(aLong);
                System.out.print("call:"+aLong+"\n");
            }
        });
        new Semaphore(0).tryAcquire(4, TimeUnit.SECONDS);
        //确认只接收到一个数字
        Assert.assertTrue(accept.size() == 1);
        Assert.assertTrue(accept.get(0) == 9L);
    }
}
