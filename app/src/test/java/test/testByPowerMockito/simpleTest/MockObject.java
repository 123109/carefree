package test.testByPowerMockito.simpleTest;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import classDefine.NormalClass;

/**
 * Created by cb on 2016/5/20.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({NormalClass.class})
public class MockObject{
    @Test
    public void testMockObject() throws Exception{
        NormalClass normalClass = PowerMockito.mock(NormalClass.class);
        normalClass.setRealValue(100);
        //normalClass是一个被mock的对象，它的所有的方法都被mock掉，所以setRealValue,getRealValue实际上都不会执行实际代码
        PowerMockito.when(normalClass.getMockValue()).thenReturn(20);
        Assert.assertTrue(normalClass.getMockValue() == 20);
        Assert.assertTrue(normalClass.getRealValue() == 0);
    }
}
