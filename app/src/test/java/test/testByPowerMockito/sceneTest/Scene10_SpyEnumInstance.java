package test.testByPowerMockito.sceneTest;

import junit.framework.Assert;

import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

import base.TestInit;
import classDefine.ClassForScene10;
import utils.MockUtils;

/**
 * 场景10：spy一个枚举单例
 * Created by cb on 2016/5/30.
 */
public class Scene10_SpyEnumInstance extends TestInit{
    @Test
    public void testSpyEnumInstance() throws Exception{
        // ClassForScene10里面有两个公共的方法：getValue和changeValue，现在我们要测试getValue，但是getValue又依赖于changeValue.
        // 由于changeValue是被测试单例的方法，我们不可能把整个ClassForScene10给mock掉，只能通过spy方法
        MockUtils.spyEnumInstance(ClassForScene10.class);
        PowerMockito.when(ClassForScene10.INSTANCE.changeValue()).thenReturn(-1);
        Assert.assertTrue(ClassForScene10.INSTANCE.getValue() == -2);
        PowerMockito.when(ClassForScene10.INSTANCE.changeValue()).thenReturn(0);
        Assert.assertTrue(ClassForScene10.INSTANCE.getValue() == 0);
        PowerMockito.when(ClassForScene10.INSTANCE.changeValue()).thenReturn(2);
        Assert.assertTrue(ClassForScene10.INSTANCE.getValue() == 2);
        PowerMockito.when(ClassForScene10.INSTANCE.changeValue()).thenReturn(10);
        Assert.assertTrue(ClassForScene10.INSTANCE.getValue() == 10);
        PowerMockito.when(ClassForScene10.INSTANCE.changeValue()).thenReturn(11);
        Assert.assertTrue(ClassForScene10.INSTANCE.getValue() == 9);
    }
    
    @Test
    public void testSpyInstance_2() throws Exception{
        //这种方法更简单些，但是必须用test这个对象进行测试，如果某个地方需要用到ClassForScene10.getInstance()懵B了……
        ClassForScene10 test = PowerMockito.spy(ClassForScene10.INSTANCE);
        PowerMockito.when(test.changeValue()).thenReturn(-1);
        Assert.assertTrue(test.getValue() == -2);
        PowerMockito.when(test.changeValue()).thenReturn(0);
        Assert.assertTrue(test.getValue() == 0);
        PowerMockito.when(test.changeValue()).thenReturn(2);
        Assert.assertTrue(test.getValue() == 2);
        PowerMockito.when(test.changeValue()).thenReturn(10);
        Assert.assertTrue(test.getValue() == 10);
        PowerMockito.when(test.changeValue()).thenReturn(11);
        Assert.assertTrue(test.getValue() == 9);
    }
}
