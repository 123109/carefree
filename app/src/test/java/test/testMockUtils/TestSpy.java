package test.testMockUtils;

import org.junit.Assert;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import base.TestInit;
import classDefine.EnumClass;
import classDefine.FinalClass;
import classDefine.InstanceClass;
import classDefine.StaticClass;
import utils.UncleMock;

/**
 * Created by Administrator on 2017/2/13.
 */
@PrepareForTest({FinalClass.class,StaticClass.class,EnumClass.class})
public class TestSpy extends TestInit{
    @Test
    public void test_spy(){
        CallOrigin unMock = new CallOrigin();
        DependencyClass dependencyClass = UncleMock.getValue(unMock,"mOne");
        Assert.assertTrue(dependencyClass != null);
        CallOrigin mock = UncleMock.spy(new CallOrigin());
        DependencyClass dependencyClassInMock = UncleMock.getValue(mock,"mOne");
        Assert.assertTrue(dependencyClassInMock != null);
    }

    @Test
    public void test_spy_final(){
        FinalClass unMock = new FinalClass();
        int mValue = UncleMock.getValue(unMock,"mValue");
        Assert.assertTrue(mValue == 100);
        FinalClass mock = UncleMock.spy(new FinalClass());
        int mockValue = UncleMock.getValue(mock,"mValue");
        Assert.assertTrue(mockValue == 100);
    }

    @Test
    public void test_spy_static(){
        int mValue = StaticClass.getMockValue();
        Assert.assertTrue(mValue == 20);
        UncleMock.spyStatic(StaticClass.class);
        int  mockValue = StaticClass.getMockValue();
        Assert.assertTrue(mockValue == 30);
        PowerMockito.when(StaticClass.getMockValue()).thenReturn(100);
        Assert.assertTrue(StaticClass.getMockValue() == 100);
    }

    @Test
    public void test_spy_instance(){
        Assert.assertTrue(InstanceClass.getInstance().getValue() == 10);
        InstanceClass mock = UncleMock.spyInstance(InstanceClass.class);
        Assert.assertTrue(InstanceClass.getInstance().getValue() == 10);
        Assert.assertTrue(mock == InstanceClass.getInstance());
        PowerMockito.when(mock.getValue()).thenReturn(100);
        Assert.assertTrue(InstanceClass.getInstance().getValue() == 100);
    }

    @Test
    public void test_spy_enumInstance(){
        Assert.assertTrue(EnumClass.INSTANCE.getValue() == 100);
        EnumClass mock = UncleMock.spyInstance(EnumClass.class);
        Assert.assertTrue(EnumClass.INSTANCE.getValue() == 100);
        Assert.assertTrue(mock == EnumClass.INSTANCE);
        PowerMockito.when(mock.getValue()).thenReturn(1000);
        Assert.assertTrue(EnumClass.INSTANCE.getValue() == 1000);
    }
}
