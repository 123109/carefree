package test.testByPowerMockito.simpleTest;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import classDefine.StaticClass;

/**
 * Created by cb on 2016/5/20.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({StaticClass.class})
public class SpyStaticClass{
    @Test
    public void testSpyStaticClass() throws Exception{
        //mock与spy的区别在于：mock不会执行原方法 直接返回我们指定的值，而spy则会执行一遍原方法 然后再返回我们指定的值
        PowerMockito.spy(StaticClass.class);
        StaticClass.setRealValue(20);
        Assert.assertTrue(StaticClass.getRealValue() == 20);
        PowerMockito.when(StaticClass.getMockValue()).thenReturn(100);
        Assert.assertTrue(StaticClass.getMockValue() == 100);
        //在getMockValue方法里，realValue被修改成realValue+10，所以调用getRealValue取到的是realValue+10
        Assert.assertTrue(StaticClass.getRealValue() == 30);
    }
}
