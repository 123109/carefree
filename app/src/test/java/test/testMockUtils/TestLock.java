package test.testMockUtils;

import org.junit.Test;

import utils.UncleMock;

/**
 * Created by Administrator on 2017/2/14.
 */

public class TestLock {
    @Test
    public void test() throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.print(i+" ");
                }
                UncleMock.release();
            }
        }).start();
        UncleMock.lock();
    }
}
