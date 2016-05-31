package test.testByPowerMockito.simpleTest;

import junit.framework.Assert;

import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import base.TestInit;
import classDefine.InstanceClass;

/**
 * Created by cb on 2016/5/30.
 */
@PrepareForTest(InstanceClass.class)
public class MockInstanceClass extends TestInit {

    @Test
    public void testSpyInstanceClass() throws Exception{
        InstanceClass mInstanceClass = PowerMockito.spy(InstanceClass.getInstance());
        PowerMockito.mockStatic(InstanceClass.class);
        PowerMockito.when(mInstanceClass.getValue()).thenReturn(2);
        PowerMockito.when(InstanceClass.getInstance()).thenReturn(mInstanceClass);
        Assert.assertTrue(mInstanceClass.getValue() == 2);
    }

    @Test
    public void testMockInstanceClass() throws Exception{
        InstanceClass mInstanceClass = PowerMockito.mock(InstanceClass.class);
        PowerMockito.mockStatic(InstanceClass.class);
        PowerMockito.when(InstanceClass.getInstance()).thenReturn(mInstanceClass);
        PowerMockito.when(mInstanceClass.getValue()).thenReturn(2);
        Assert.assertTrue(mInstanceClass.getValue() == 2);
    }
}
