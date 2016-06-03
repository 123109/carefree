package test.testLimit;

import junit.framework.Assert;

import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

import test.testLimit.classDefine.TestClass;
import test.testLimit.classDefine.TestMessage;

/**
 * Created by cb on 2016/6/2.
 */
public class Limit {
    @Test
    public void testLimit(){
        TestMessage message = PowerMockito.mock(TestMessage.class);
        PowerMockito.when(message.isValid()).thenReturn(true);
        TestClass testClass = new TestClass();
        Assert.assertTrue(testClass.onMessageReceived(message));
        PowerMockito.when(message.isValid()).thenReturn(false);
        Assert.assertFalse(testClass.onMessageReceived(message));
        //在上面这个测试里，如果TestClass里的这行代码“TestCache.INSTANCE.addToCache(message);”漏写了，对于这个case是没有影响的，但是实际上这里隐藏了一个BUG
    }
}
