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
public class MockStaticClass{
    @Test
    public void testMockStaticClass() throws Exception{
        PowerMockito.mockStatic(StaticClass.class);
        StaticClass.setRealValue(20);
        //StaticClass被mock后，它的所有的方法都被mock掉，所以setRealValue,getRealValue实际上都不会执行实际代码
        PowerMockito.when(StaticClass.getMockValue()).thenReturn(10);
        Assert.assertTrue(StaticClass.getMockValue() == 10);
        Assert.assertTrue(StaticClass.getRealValue() == 0);
    }
}
