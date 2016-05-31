package test.testByPowerMockito.sceneTest;

import junit.framework.Assert;

import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.reflect.Whitebox;

import base.TestInit;
import classDefine.ClassForScene10;

/**
 * 场景10：spy一个枚举单例
 * Created by cb on 2016/5/30.
 */
@PrepareForTest(ClassForScene10.class)
public class Scene10_SpyEnumInstance extends TestInit{
    @Test
    public void testSpyEnumInstance() throws Exception{
        ClassForScene10 spy = PowerMockito.spy(ClassForScene10.INSTANCE);
        Whitebox.setInternalState(ClassForScene10.INSTANCE,"INSTANCE",spy,ClassForScene10.class);
        PowerMockito.when(spy.changeValue()).thenReturn(-1);
        Assert.assertTrue(ClassForScene10.INSTANCE.getValue() == -2);
        PowerMockito.when(spy.changeValue()).thenReturn(0);
        Assert.assertTrue(ClassForScene10.INSTANCE.getValue() == 0);
        PowerMockito.when(spy.changeValue()).thenReturn(2);
        Assert.assertTrue(ClassForScene10.INSTANCE.getValue() == 2);
        PowerMockito.when(spy.changeValue()).thenReturn(10);
        Assert.assertTrue(ClassForScene10.INSTANCE.getValue() == 10);
        PowerMockito.when(spy.changeValue()).thenReturn(11);
        Assert.assertTrue(ClassForScene10.INSTANCE.getValue() == 9);
    }
}
