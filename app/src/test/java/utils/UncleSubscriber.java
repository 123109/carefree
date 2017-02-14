package utils;

import org.mockito.exceptions.base.MockitoAssertionError;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/2/10.
 */
public class UncleSubscriber<T>{

    private boolean mIsCompleted = false;
    private T mData;
    private Throwable mThrowable;
    private Semaphore mSemaphore;
    private boolean mIsNextCalled = false;
    private Subscriber<T> mSubscriber;
    UncleSubscriber(){
        mSubscriber = new Subscriber<T>() {
            @Override
            public void onCompleted() {
                mIsCompleted = true;
            }

            @Override
            public void onError(final Throwable e) {
                mThrowable = e;
                mSemaphore.release();
            }

            @Override
            public void onNext(final T t) {
                mIsNextCalled = true;
                mData = t;
                mSemaphore.release();
            }
        };
    }

    Subscriber<T> getSubscriber() {
        return mSubscriber;
    }

    public void waitTerminal() throws InterruptedException {
        mSemaphore.tryAcquire(120, TimeUnit.SECONDS);
    }

    public void waitTerminal(long count,TimeUnit unit) throws InterruptedException {
        mSemaphore.tryAcquire(count, unit);
    }

    void setSemaphore(final Semaphore semaphore) {
        mSemaphore = semaphore;
    }

    public boolean isCompleted() {
        return mIsCompleted;
    }

    public T getData() {
        if (mIsNextCalled && !mIsCompleted) {
            MockitoAssertionError error = new MockitoAssertionError("你的observable代码中onCompleted 未被调用，可能隐藏风险");
            error.printStackTrace();
        }
        return mData;
    }

    public Throwable getThrowable() {
        return mThrowable;
    }
}
