package rxTest;

import junit.framework.Assert;

import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import base.TestInit;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;

/**
 * 一个定时触发的任务，可以当作一个定时器来用
 * Created by cb on 2016/7/26.
 */
public class testInterval extends TestInit{
    @Test
    public void testInterval_raw() throws InterruptedException {
        final ArrayList<Long> data = new ArrayList<>();
        Observable.interval(1, TimeUnit.SECONDS).subscribe(new Action1<Long>() {
            @Override
            public void call(final Long aLong) {
                data.add(aLong);
                System.out.print(aLong+"\n");
            }
        });
        new Semaphore(0).tryAcquire(5, TimeUnit.SECONDS);
        Assert.assertTrue(data.size() == 5);
        for (int i = 0 ;i < 5; i++){
            Assert.assertTrue(data.get(i).intValue() == i);
        }
    }

    @Test
    public void testInterval_raw_unSubscribe() throws InterruptedException {
        final ArrayList<Long> data = new ArrayList<>();
        final Subscription subscription = Observable.interval(1, TimeUnit.SECONDS).subscribe(new Action1<Long>() {
            @Override
            public void call(final Long aLong) {
                data.add(aLong);
                System.out.print(aLong + "\n");
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //3秒后把观察者反注册掉
                    Thread.sleep(3000);
                    subscription.unsubscribe();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Semaphore(0).tryAcquire(5, TimeUnit.SECONDS);
        //确认只发出三个数字
        Assert.assertTrue(data.size() == 3);
    }

    @Test
    public void testInterval_subscriber() throws InterruptedException {
        Subscriber<Long> subscriber = new Subscriber<Long>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(final Throwable e) {

            }

            @Override
            public void onNext(final Long aLong) {
                System.out.print(aLong+"\n");
            }
        };
        Observable.interval(1, TimeUnit.SECONDS).subscribe(subscriber);
        new Semaphore(0).tryAcquire(5, TimeUnit.SECONDS);
    }

}
