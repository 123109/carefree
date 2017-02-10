package utils;

import org.mockito.exceptions.base.MockitoAssertionError;

import java.util.concurrent.Semaphore;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/2/10.
 */
public class MockSubscriber<T> extends Subscriber<T> {

    private boolean mIsCompleted = false;
    private T mData;
    private Throwable mThrowable;
    private Semaphore mSemaphore;
    private boolean mIsNextCalled = false;
    MockSubscriber(){}

    void setSemaphore(final Semaphore semaphore) {
        mSemaphore = semaphore;
    }

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
