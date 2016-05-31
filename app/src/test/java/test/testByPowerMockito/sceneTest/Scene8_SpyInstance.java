package test.testByPowerMockito.sceneTest;

import junit.framework.Assert;

import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

import base.TestInit;
import classDefine.ClassForScene8;
import utils.MockUtils;

/**
 * 场景8：spy一个单例
 * Created by cb on 2016/5/30.
 */
public class Scene8_SpyInstance extends TestInit{
    @Test
    public void testSpyInstance() throws Exception{
        // ClassForScene8里面有两个公共的方法：getValue和changeValue，现在我们要测试getValue，但是getValue又依赖于changeValue.
        // 由于changeValue是被测试单例的方法，我们不可能把整个ClassForScene8给mock掉，只能通过spy方法
        MockUtils.spyInstanceClass(ClassForScene8.class);
        PowerMockito.when(ClassForScene8.getInstance().changeValue()).thenReturn(-1);
        Assert.assertTrue(ClassForScene8.getInstance().getValue() == -2);
        PowerMockito.when(ClassForScene8.getInstance().changeValue()).thenReturn(0);
        Assert.assertTrue(ClassForScene8.getInstance().getValue() == 0);
        PowerMockito.when(ClassForScene8.getInstance().changeValue()).thenReturn(2);
        Assert.assertTrue(ClassForScene8.getInstance().getValue() == 2);
        PowerMockito.when(ClassForScene8.getInstance().changeValue()).thenReturn(10);
        Assert.assertTrue(ClassForScene8.getInstance().getValue() == 10);
        PowerMockito.when(ClassForScene8.getInstance().changeValue()).thenReturn(11);
        Assert.assertTrue(ClassForScene8.getInstance().getValue() == 9);
    }
    
    @Test
    public void testSpyInstance_2() throws Exception{
        //这种方法更简单些，但是必须用test这个对象进行测试，如果某个地方需要用到ClassForScene8.getInstance()懵B了……
        ClassForScene8 test = PowerMockito.spy(ClassForScene8.getInstance());
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
