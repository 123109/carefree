package rxTest;

import junit.framework.Assert;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import base.TestInit;
import rx.Observable;
import rx.functions.Action1;
import rx.subjects.PublishSubject;

/**
 * 把一段时间内
 * Created by cb on 2016/7/26.
 */
public class testBuffer extends TestInit {

    private int mCallCount;

    @Test
    public void testBuffer_Count() throws InterruptedException {
        mCallCount = 0;
        final List<Long> accept = new ArrayList<>();
        final PublishSubject<Long> subject = PublishSubject.create();
        //指定缓冲的数量是10个
        final int count = 10;
        subject.buffer(count).subscribe(new Action1<List<Long>>() {
            @Override
            public void call(final List<Long> longs) {
                //把收集到的数据都添加到accept数组里
                accept.addAll(longs);
                Assert.assertTrue(longs.size() <= count);
                mCallCount++;
            }
        });

        //开启一个线程，每100毫秒调一次subject.onNext
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (Long aLong = 0L;aLong<20;aLong++){
                        Thread.sleep(100);
                        subject.onNext(aLong);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Semaphore(0).tryAcquire(3, TimeUnit.SECONDS);
        Assert.assertTrue(accept.size() == 20);
        Assert.assertTrue(mCallCount == 2);
    }

    @Test
    public void testBuffer_Time() throws InterruptedException {
        mCallCount = 0;
        final List<Long> accept = new ArrayList<>();
        final PublishSubject<Long> subject = PublishSubject.create();
        //指定缓冲的时间是1秒钟
        subject.buffer(1, TimeUnit.SECONDS).subscribe(new Action1<List<Long>>() {
            @Override
            public void call(final List<Long> longs) {
                //把收集到的数据都添加到accept数组里
                accept.addAll(longs);
                mCallCount ++;
            }
        });

        //开启一个线程，每50毫秒调一次subject.onNext
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (Long aLong = 0L;aLong<20;aLong++){
                        Thread.sleep(48);
                        subject.onNext(aLong);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Semaphore(0).tryAcquire(1800, TimeUnit.MILLISECONDS);
        System.out.print("size:" + accept.size());
        Assert.assertTrue(accept.size() == 20);
        Assert.assertTrue(mCallCount == 1);
    }

    @Test
    public void testBuffer_TimeExceed() throws InterruptedException {
        mCallCount = 0;
        final List<Long> accept = new ArrayList<>();
        final PublishSubject<Long> subject = PublishSubject.create();
        //指定缓冲的时间是3秒钟
        subject.buffer(3,TimeUnit.SECONDS).subscribe(new Action1<List<Long>>() {
            @Override
            public void call(final List<Long> longs) {
                //把收集到的数据都添加到accept数组里
                accept.addAll(longs);
                mCallCount ++;
            }
        });

        //开启一个线程，每100毫秒调一次subject.onNext
        Observable.interval(100, TimeUnit.MILLISECONDS).subscribe(new Action1<Long>() {
            @Override
            public void call(final Long aLong) {
                subject.onNext(aLong);
            }
        });

        new Semaphore(0).tryAcquire(2, TimeUnit.SECONDS);
        Assert.assertTrue(accept.size() == 0);
        Assert.assertTrue(mCallCount == 0);
    }
}
