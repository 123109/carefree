package test.testByPowerMockito.simpleTest;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import classDefine.FinalClass;

/**
 * Created by cb on 2016/5/20.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({FinalClass.class})
public class MockFinalClass{
    @Test
    public void testMockFinalClass() throws Exception{
        //finalClass里面有两个方法 ，一个是静态的，一个是非静态的
        //mock静态的方法
        PowerMockito.mockStatic(FinalClass.class);
        PowerMockito.when(FinalClass.getValue()).thenReturn(-1);
        Assert.assertTrue(FinalClass.getValue() == -1);

        //mock非静态的方法
        FinalClass finalClass = PowerMockito.mock(FinalClass.class);
        PowerMockito.when(finalClass.getMockValue()).thenReturn(-1);
        Assert.assertTrue(finalClass.getMockValue() == -1);
    }
}
