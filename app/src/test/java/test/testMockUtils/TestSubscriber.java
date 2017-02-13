package test.testMockUtils;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import base.TestInit;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import utils.UncleSubscriber;
import utils.UncleMock;

/**
 * Created by Administrator on 2017/2/13.
 */

public class TestSubscriber extends TestInit{
    @Test
    public void test_just() throws InterruptedException {
        UncleSubscriber<Integer> subscriber = UncleMock.mockSubscriber(Observable.just(1));
        Assert.assertTrue(subscriber.isCompleted());
        Assert.assertTrue(subscriber.getData() == 1);
    }

    @Test
    public void test_timeCost_mainThread() throws InterruptedException {
        UncleSubscriber<Integer> subscriber = UncleMock.mockSubscriber(Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(final Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 10; i++) {
                    try {
                        Thread.sleep(100);
                        System.out.print("call\n");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                subscriber.onNext(1);
                subscriber.onCompleted();
            }
        }));
        Assert.assertTrue(subscriber.isCompleted());
        Assert.assertTrue(subscriber.getData() == 1);
    }

    @Test
    public void test_timeCost_thread_wait() throws InterruptedException {
        UncleSubscriber<Integer> subscriber = UncleMock.mockSubscriber(Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(final Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 10; i++) {
                    try {
                        Thread.sleep(100);
                        System.out.print("call\n");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                subscriber.onNext(1);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread()));
        subscriber.waitTerminal(100, TimeUnit.MILLISECONDS);
        Assert.assertTrue(!subscriber.isCompleted());
        Assert.assertTrue(subscriber.getData() == null);
    }

    @Test
    public void test_timeCost_thread_waitTillTerminal() throws InterruptedException {
        UncleSubscriber<Integer> subscriber = UncleMock.mockSubscriber(Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(final Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 10; i++) {
                    try {
                        Thread.sleep(100);
                        System.out.print("call\n");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                subscriber.onNext(1);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread()));
        subscriber.waitTerminal();
        Assert.assertTrue(subscriber.isCompleted());
        Assert.assertTrue(subscriber.getData() == 1);
    }
}
